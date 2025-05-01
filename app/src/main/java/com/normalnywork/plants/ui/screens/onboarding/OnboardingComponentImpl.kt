package com.normalnywork.plants.ui.screens.onboarding

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.normalnywork.plants.ui.navigation.screen.OnboardingComponent
import kotlinx.coroutines.flow.MutableStateFlow

class OnboardingComponentImpl(
    componentContext: ComponentContext,
    private val onFinish: () -> Unit,
) : OnboardingComponent, ComponentContext by componentContext {

    private val stateHolder = instanceKeeper.getOrCreate { StateHolder() }

    override val currentPage = MutableStateFlow(stateHolder.currentPage)

    init {
        backHandler.register(
            BackCallback {
                if (stateHolder.currentPage > 0) {
                    stateHolder.currentPage--
                    currentPage.value = stateHolder.currentPage
                }
            }
        )
    }

    override fun next() {
        if (stateHolder.currentPage >= 2) onFinish()
        else {
            stateHolder.currentPage++
            currentPage.value = stateHolder.currentPage
        }
    }

    class StateHolder : InstanceKeeper.Instance {

        var currentPage: Int = 0
    }
}