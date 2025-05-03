package com.normalnywork.plants.ui.screens.plants

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.normalnywork.plants.ui.navigation.screen.PlantsComponent

@Composable
fun PlantsScreen(component: PlantsComponent) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Hello world!")
    }
}