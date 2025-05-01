package com.normalnywork.plants.ui.screens.onboarding

import com.arkivanov.decompose.ComponentContext
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

    override fun next() {
        if (++stateHolder.currentPage >= 3) onFinish()
        else currentPage.value = stateHolder.currentPage
    }

    class StateHolder : InstanceKeeper.Instance {

        var currentPage: Int = 0
    }
}