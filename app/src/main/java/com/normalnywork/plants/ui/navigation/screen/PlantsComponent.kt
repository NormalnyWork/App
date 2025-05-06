package com.normalnywork.plants.ui.navigation.screen

import com.normalnywork.plants.domain.entity.Plant
import kotlinx.coroutines.flow.StateFlow

interface PlantsComponent {

    val isLoading: StateFlow<Boolean>

    val plants: StateFlow<List<Plant>>
}