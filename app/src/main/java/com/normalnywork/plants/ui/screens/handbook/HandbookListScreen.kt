package com.normalnywork.plants.ui.screens.handbook

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.normalnywork.plants.ui.kit.components.AppTopBar
import com.normalnywork.plants.ui.navigation.screen.HandbookListComponent

@Composable
fun HandbookListScreen(component: HandbookListComponent) {
    Box(modifier = Modifier.fillMaxSize()) {
        AppTopBar(title = "Handbook")
    }
}