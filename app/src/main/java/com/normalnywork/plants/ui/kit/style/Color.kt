package com.normalnywork.plants.ui.kit.style

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalAppColors = compositionLocalOf<AppColors> { error("No AppColors provided") }

data class AppColors(
    val background: Color,
    val primary: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val stroke: Color,
    val strokeSecondary: Color,
    val error: Color,
) {

    companion object {

        val Light = AppColors(
            background = Color(0xFFFFFFFF),
            primary = Color(0xFF1E9B69),
            textPrimary = Color(0xFF000000),
            textSecondary = Color(0xFF888888),
            stroke = Color(0xFFC3C3C3),
            strokeSecondary = Color(0xFFF2F2F2),
            error = Color(0xFFEE3E41),
        )

        val Dark = AppColors(
            background = Color(0xFF121212),
            primary = Color(0xFF35C68C),
            textPrimary = Color(0xFFFFFFFF),
            textSecondary = Color(0xFF888888),
            stroke = Color(0xFF5D5D5D),
            strokeSecondary = Color(0xFF242424),
            error = Color(0xFFD56062),
        )
    }
}