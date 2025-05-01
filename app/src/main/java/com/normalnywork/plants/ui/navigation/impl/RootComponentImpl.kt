package com.normalnywork.plants.ui.navigation.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.normalnywork.plants.ui.navigation.flow.RootComponent
import com.normalnywork.plants.ui.navigation.flow.RootComponent.RootConfig
import com.normalnywork.plants.ui.navigation.flow.RootComponent.RootScreen
import com.normalnywork.plants.ui.screens.onboarding.OnboardingComponentImpl

class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootConfig>()
    private val initialConfiguration = RootConfig.Onboarding

    override val childStack: Value<ChildStack<*, RootScreen>> =
        childStack(
            source = navigation,
            serializer = RootConfig.serializer(),
            initialConfiguration = initialConfiguration,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    private fun createChild(
        config: RootConfig,
        componentContext: ComponentContext
    ): RootScreen = when (config) {
        RootConfig.Onboarding -> onboardingScreen(componentContext)
    }

    private fun onboardingScreen(componentContext: ComponentContext) =
        RootScreen.Onboarding(
            component = OnboardingComponentImpl(
                componentContext = componentContext,
                onFinish = { navigation.replaceCurrent(RootConfig.Onboarding) } // TODO
            )
        )
}