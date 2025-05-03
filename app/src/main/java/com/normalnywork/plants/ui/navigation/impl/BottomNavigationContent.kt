package com.normalnywork.plants.ui.navigation.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.normalnywork.plants.ui.navigation.flow.BottomNavigationComponent
import com.normalnywork.plants.ui.navigation.flow.BottomNavigationComponent.BottomNavigationScreen
import com.normalnywork.plants.ui.screens.tasks.TasksScreen

@Composable
fun BottomNavigationContent(component: BottomNavigationComponent) {
    Children(
        stack = component.childStack,
        modifier = Modifier.fillMaxSize(),
    ) { child ->
        when (val screen = child.instance) {
            is BottomNavigationScreen.TasksScreen -> TasksScreen(screen.component)
        }
    }
}