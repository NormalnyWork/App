package com.normalnywork.plants.ui.navigation.flow

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.normalnywork.plants.ui.navigation.screen.PlantsComponent
import com.normalnywork.plants.ui.navigation.screen.TasksComponent
import kotlinx.serialization.Serializable

interface BottomNavigationComponent {

    val childStack: Value<ChildStack<*, BottomNavigationScreen>>

    fun selectNewTab(tab: BottomNavigationConfig)

    @Serializable
    sealed interface BottomNavigationConfig {

        @Serializable
        data object Tasks : BottomNavigationConfig

        @Serializable
        data object Plants : BottomNavigationConfig

        @Serializable
        data object Handbook : BottomNavigationConfig
    }

    sealed class BottomNavigationScreen {

        data class TasksScreen(val component: TasksComponent) : BottomNavigationScreen()

        data class PlantsScreen(val component: PlantsComponent) : BottomNavigationScreen()

        data class HandbookScreen(val component: HandbookNavigationComponent) : BottomNavigationScreen()
    }
}