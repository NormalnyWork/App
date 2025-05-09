package com.normalnywork.plants.ui.kit.style

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

val LocalAppShapes = staticCompositionLocalOf<AppShapes> { provideAppShapes() }

private fun provideAppShapes(): AppShapes {
    return AppShapes(
        extraLarge = CircleShape,
        large = RoundedCornerShape(24.dp),
        medium = RoundedCornerShape(16.dp),
        small = RoundedCornerShape(12.dp),
        extraSmall = RoundedCornerShape(8.dp),
    )
}

data class AppShapes(
    val extraLarge: Shape,
    val large: Shape,
    val medium: Shape,
    val small: Shape,
    val extraSmall: Shape,
)