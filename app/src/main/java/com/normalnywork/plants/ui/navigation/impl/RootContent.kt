package com.normalnywork.plants.ui.navigation.impl

import Android13NavigationTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.navigation.flow.RootComponent
import com.normalnywork.plants.ui.navigation.flow.RootComponent.RootScreen
import com.normalnywork.plants.ui.screens.auth.SignInScreen
import com.normalnywork.plants.ui.screens.auth.SignUpScreen
import com.normalnywork.plants.ui.screens.onboarding.OnboardingScreen

@Composable
fun RootContent(component: RootComponent) {
    Children(
        stack = component.childStack,
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background),
        animation = stackAnimation(animator = Android13NavigationTransition())
    ) { child ->
        when (val screen = child.instance) {
            is RootScreen.Onboarding -> OnboardingScreen(component = screen.component)
            is RootScreen.SignUp -> SignUpScreen(component = screen.component)
            is RootScreen.SignIn -> SignInScreen(component = screen.component)
            is RootScreen.BottomNavigation -> BottomNavigationContent(component = screen.component)
        }
    }
}