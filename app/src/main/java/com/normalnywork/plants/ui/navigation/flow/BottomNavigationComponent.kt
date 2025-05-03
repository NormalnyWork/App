package com.normalnywork.plants.ui.navigation.flow

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.normalnywork.plants.ui.navigation.screen.TasksComponent
import kotlinx.serialization.Serializable

interface BottomNavigationComponent {

    val childStack: Value<ChildStack<*, BottomNavigationScreen>>

    fun selectNewTab(tab: BottomNavigationConfig)

    @Serializable
    sealed interface BottomNavigationConfig {

        @Serializable
        data object Tasks : BottomNavigationConfig
    }

    sealed class BottomNavigationScreen {

        data class TasksScreen(val component: TasksComponent) : BottomNavigationScreen()
    }
}