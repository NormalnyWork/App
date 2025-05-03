package com.normalnywork.plants.ui.navigation.impl.content

import Android13NavigationTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.navigation.flow.HandbookNavigationComponent
import com.normalnywork.plants.ui.navigation.flow.HandbookNavigationComponent.HandbookScreen
import com.normalnywork.plants.ui.screens.handbook.HandbookArticleScreen
import com.normalnywork.plants.ui.screens.handbook.HandbookListScreen

@Composable
fun HandbookNavigationContent(component: HandbookNavigationComponent) {
    Children(
        stack = component.childStack,
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background),
        animation = stackAnimation(animator = Android13NavigationTransition())
    ) { child ->
        when (val screen = child.instance) {
            is HandbookScreen.List -> HandbookListScreen(screen.component)
            is HandbookScreen.Article -> HandbookArticleScreen(screen.component)
        }
    }
}