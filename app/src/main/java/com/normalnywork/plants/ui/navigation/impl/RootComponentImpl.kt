package com.normalnywork.plants.ui.navigation.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.normalnywork.plants.data.api.auth.TokenStore
import com.normalnywork.plants.ui.navigation.flow.RootComponent
import com.normalnywork.plants.ui.navigation.flow.RootComponent.RootConfig
import com.normalnywork.plants.ui.navigation.flow.RootComponent.RootScreen
import com.normalnywork.plants.ui.screens.auth.SignInComponentImpl
import com.normalnywork.plants.ui.screens.auth.SignUpComponentImpl
import com.normalnywork.plants.ui.screens.onboarding.OnboardingComponentImpl
import com.normalnywork.plants.utils.Prefs
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext, KoinComponent {

    private val tokenStore: TokenStore by inject()

    private val navigation = StackNavigation<RootConfig>()
    private val initialConfiguration = when {
        !Prefs.onboardingShown -> RootConfig.Onboarding
        tokenStore.getAccessToken() != null -> RootConfig.BottomNavigation
        else -> RootConfig.SignIn // TODO
    }

    override val childStack: Value<ChildStack<*, RootScreen>> =
        childStack(
            source = navigation,
            serializer = RootConfig.serializer(),
            initialConfiguration = initialConfiguration,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    init { checkIfLoggedIn() }

    private fun checkIfLoggedIn() {
        if (tokenStore.getAccessToken() == null
            && Prefs.onboardingShown
            && childStack.active.instance !is RootScreen.SignIn
            && childStack.active.instance !is RootScreen.SignUp
        ) {
            navigation.replaceCurrent(RootConfig.SignIn)
        }
    }

    private fun createChild(
        config: RootConfig,
        componentContext: ComponentContext,
    ): RootScreen = when (config) {
        RootConfig.Onboarding -> onboardingScreen(componentContext)
        RootConfig.SignUp -> signUpScreen(componentContext)
        RootConfig.SignIn -> signInScreen(componentContext)
        RootConfig.BottomNavigation -> bottomNavigationScreen(componentContext)
    }

    private fun onboardingScreen(componentContext: ComponentContext) =
        RootScreen.Onboarding(
            component = OnboardingComponentImpl(
                componentContext = componentContext,
                onFinish = { navigation.replaceCurrent(RootConfig.SignIn) },
            )
        )

    private fun signUpScreen(componentContext: ComponentContext) =
        RootScreen.SignUp(
            component = SignUpComponentImpl(
                componentContext = componentContext,
                navigateToSignIn = { navigation.replaceCurrent(RootConfig.SignIn) },
                navigateToHome = { navigation.replaceCurrent(RootConfig.BottomNavigation) },
            )
        )

    private fun signInScreen(componentContext: ComponentContext) =
        RootScreen.SignIn(
            component = SignInComponentImpl(
                componentContext = componentContext,
                navigateToSignUp = { navigation.replaceCurrent(RootConfig.SignUp) },
                navigateToHome = { navigation.replaceCurrent(RootConfig.BottomNavigation) },
            )
        )

    private fun bottomNavigationScreen(componentContext: ComponentContext) =
        RootScreen.BottomNavigation(
            component = BottomNavigationComponentImpl(
                componentContext = componentContext,
            )
        )
}