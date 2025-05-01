package com.normalnywork.plants.ui.kit.style

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalAppColors = compositionLocalOf<AppColors> { error("No AppColors provided") }

data class AppColors(
    val background: Color,
    val primary: Color,
    val textPrimary: Color,
) {

    companion object {

        val Light = AppColors(
            background = Color(0xFFFFFFFF),
            primary = Color(0xFF1E9B69),
            textPrimary = Color(0xFF000000)
        )
    }
}