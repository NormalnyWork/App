package com.normalnywork.plants.ui.navigation.flow

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.normalnywork.plants.ui.navigation.screen.OnboardingComponent
import kotlinx.serialization.Serializable

interface RootComponent {

    val childStack: Value<ChildStack<*, RootScreen>>

    @Serializable
    sealed interface RootConfig {

        @Serializable
        data object Onboarding : RootConfig
    }

    sealed class RootScreen {

        class Onboarding(val component: OnboardingComponent) : RootScreen()
    }
}