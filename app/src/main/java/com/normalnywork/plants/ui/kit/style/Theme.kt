package com.normalnywork.plants.ui.kit.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme {
        CompositionLocalProvider(
            LocalAppColors provides if (isDarkTheme) AppColors.Dark else AppColors.Light,
            content = content,
        )
    }
}