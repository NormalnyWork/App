package com.normalnywork.plants.ui.navigation.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.normalnywork.plants.domain.entity.Plant
import com.normalnywork.plants.ui.navigation.flow.BottomNavigationComponent
import com.normalnywork.plants.ui.navigation.flow.BottomNavigationComponent.BottomNavigationConfig
import com.normalnywork.plants.ui.navigation.flow.BottomNavigationComponent.BottomNavigationScreen
import com.normalnywork.plants.ui.screens.plants.PlantsComponentImpl
import com.normalnywork.plants.ui.screens.tasks.TasksComponentImpl

class BottomNavigationComponentImpl(
    componentContext: ComponentContext,
    private val navigateToCreatePlant: () -> Unit,
    private val navigateToEditPlant: (Plant) -> Unit,
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
        BottomNavigationConfig.Plants -> plantsScreen(componentContext)
        BottomNavigationConfig.Handbook -> handbookScreen(componentContext)
    }

    private fun tasksScreen(componentContext: ComponentContext) =
        BottomNavigationScreen.TasksScreen(
            component = TasksComponentImpl(
                componentContext = componentContext,
            )
        )

    private fun handbookScreen(componentContext: ComponentContext) =
        BottomNavigationScreen.HandbookScreen(
            component = HandbookNavigationComponentImpl(
                componentContext = componentContext,
            )
        )

    private fun plantsScreen(componentContext: ComponentContext) =
        BottomNavigationScreen.PlantsScreen(
            component = PlantsComponentImpl(
                componentContext = componentContext,
                navigateToCreatePlant = navigateToCreatePlant,
                navigateToEditPlant = navigateToEditPlant,
            )
        )
}