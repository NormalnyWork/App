package com.normalnywork.plants.ui.navigation.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.normalnywork.plants.ui.navigation.flow.HandbookNavigationComponent
import com.normalnywork.plants.ui.navigation.flow.HandbookNavigationComponent.HandbookConfig
import com.normalnywork.plants.ui.navigation.flow.HandbookNavigationComponent.HandbookScreen
import com.normalnywork.plants.ui.screens.handbook.HandbookArticleComponentImpl
import com.normalnywork.plants.ui.screens.handbook.HandbookListComponentImpl

class HandbookNavigationComponentImpl(
    componentContext: ComponentContext,
) : HandbookNavigationComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<HandbookConfig>()
    private val initialConfiguration = HandbookConfig.List

    override val childStack: Value<ChildStack<*, HandbookScreen>> =
        childStack(
            source = navigation,
            serializer = HandbookConfig.serializer(),
            initialConfiguration = initialConfiguration,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    private fun createChild(
        config: HandbookConfig,
        componentContext: ComponentContext,
    ): HandbookScreen = when (config) {
        HandbookConfig.List -> listScreen(componentContext)
        is HandbookConfig.Article -> articleScreen(componentContext, config.id)
    }

    private fun listScreen(componentContext: ComponentContext) =
        HandbookScreen.List(
            component = HandbookListComponentImpl(
                componentContext = componentContext,
            )
        )

    private fun articleScreen(
        componentContext: ComponentContext,
        id: Int,
    ) = HandbookScreen.Article(
        component = HandbookArticleComponentImpl(
            componentContext = componentContext,
            id = id,
        )
    )
}