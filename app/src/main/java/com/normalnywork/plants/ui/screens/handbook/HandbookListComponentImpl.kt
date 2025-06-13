package com.normalnywork.plants.ui.screens.handbook

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.normalnywork.plants.domain.entity.Guide
import com.normalnywork.plants.domain.repository.PlantsRepository
import com.normalnywork.plants.ui.navigation.screen.HandbookListComponent
import com.normalnywork.plants.utils.componentScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HandbookListComponentImpl(
    componentContext: ComponentContext,
    private val openGuide: (Guide) -> Unit,
) : HandbookListComponent, ComponentContext by componentContext, KoinComponent {

    private val plantsRepository: PlantsRepository by inject()

    private val stateHolder = instanceKeeper.getOrCreate { StateHolder() }

    override val guides = MutableStateFlow(stateHolder.guides)
    override val searchRequest = MutableStateFlow(stateHolder.searchRequest)

    init {
        componentScope.launch {
            plantsRepository.getGuidesList().let {
                stateHolder.guides = it
                onSearchRequestChanged(searchRequest.value)
            }
        }
    }

    override fun onSearchRequestChanged(newSearchRequest: String) {
        stateHolder.searchRequest = newSearchRequest
        searchRequest.value = newSearchRequest
        guides.value = stateHolder.guides.filter {
            it.name
                .split(' ')
                .any { word -> word.startsWith(newSearchRequest.trim(), ignoreCase = true) }
        }
    }

    override fun goToGuide(guide: Guide) = openGuide(guide)

    class StateHolder : InstanceKeeper.Instance {

        var guides = emptyList<Guide>()
        var searchRequest = ""
    }
}