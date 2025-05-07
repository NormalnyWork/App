package com.normalnywork.plants.ui.screens.plants

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.doOnResume
import com.normalnywork.plants.domain.entity.Plant
import com.normalnywork.plants.domain.repository.PlantsRepository
import com.normalnywork.plants.ui.navigation.screen.PlantsComponent
import com.normalnywork.plants.utils.componentScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlantsComponentImpl(
    componentContext: ComponentContext,
    private val navigateToCreatePlant: () -> Unit,
) : PlantsComponent, ComponentContext by componentContext, KoinComponent {

    private val plantsRepository: PlantsRepository by inject()

    private val stateHolder = instanceKeeper.getOrCreate { StateHolder() }

    override val isLoading = MutableStateFlow(false)
    override val plants = MutableStateFlow(stateHolder.plants)

    init {
        doOnResume {
            loadPlants()
        }
    }

    override fun createPlant() = navigateToCreatePlant()

    private fun loadPlants() {
        componentScope.launch {
            runCatching {
                isLoading.value = true
                plantsRepository.getPlants()
            }.onSuccess {
                stateHolder.plants = it
                plants.value = it
            }.onFailure {
                it.printStackTrace()
            }.also {
                isLoading.value = false
            }
        }
    }

    class StateHolder : InstanceKeeper.Instance {

        var plants: List<Plant> = emptyList()
    }
}