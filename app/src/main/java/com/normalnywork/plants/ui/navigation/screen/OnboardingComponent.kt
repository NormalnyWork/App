package com.normalnywork.plants.ui.navigation.screen

import kotlinx.coroutines.flow.StateFlow

interface OnboardingComponent {

    val currentPage: StateFlow<Int>

    fun next()
}