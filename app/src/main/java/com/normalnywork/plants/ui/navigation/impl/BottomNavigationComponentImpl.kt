package com.normalnywork.plants.ui.navigation.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.normalnywork.plants.ui.navigation.flow.BottomNavigationComponent
import com.normalnywork.plants.ui.navigation.flow.BottomNavigationComponent.BottomNavigationConfig
import com.normalnywork.plants.ui.navigation.flow.BottomNavigationComponent.BottomNavigationScreen
import com.normalnywork.plants.ui.screens.tasks.TasksComponentImpl

class BottomNavigationComponentImpl(
    componentContext: ComponentContext
) : BottomNavigationComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<BottomNavigationConfig>()
    private val initialConfiguration = BottomNavigationConfig.Tasks

    override val childStack: Value<ChildStack<*, BottomNavigationScreen>> =
        childStack(
            source = navigation,
            serializer = BottomNavigationConfig.serializer(),
            initialConfiguration = initialConfiguration,
            handleBackButton = true,
            childFactory = ::createScreen,
        )

    override fun selectNewTab(tab: BottomNavigationConfig) = navigation.bringToFront(tab)

    private fun createScreen(
        config: BottomNavigationConfig,
        componentContext: ComponentContext,
    ): BottomNavigationScreen = when (config) {
        BottomNavigationConfig.Tasks -> tasksScreen(componentContext)
    }

    private fun tasksScreen(componentContext: ComponentContext) =
        BottomNavigationScreen.TasksScreen(
            component = TasksComponentImpl(
                componentContext = componentContext,
            )
        )
}