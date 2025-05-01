package com.normalnywork.plants.ui.navigation.flow

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.normalnywork.plants.ui.navigation.screen.OnboardingComponent
import com.normalnywork.plants.ui.navigation.screen.SignInComponent
import com.normalnywork.plants.ui.navigation.screen.SignUpComponent
import kotlinx.serialization.Serializable

interface RootComponent {

    val childStack: Value<ChildStack<*, RootScreen>>

    @Serializable
    sealed interface RootConfig {

        @Serializable
        data object Onboarding : RootConfig

        @Serializable
        data object SignUp : RootConfig

        @Serializable
        data object SignIn : RootConfig
    }

    sealed class RootScreen {

        class Onboarding(val component: OnboardingComponent) : RootScreen()

        class SignUp(val component: SignUpComponent) : RootScreen()

        class SignIn(val component: SignInComponent) : RootScreen()
    }
}