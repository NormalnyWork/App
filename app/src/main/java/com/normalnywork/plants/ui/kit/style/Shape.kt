package com.normalnywork.plants.ui.kit.style

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

val LocalAppShapes = staticCompositionLocalOf<AppShapes> { provideAppShapes() }

private fun provideAppShapes(): AppShapes {
    return AppShapes(
        large = CircleShape,
        medium = RoundedCornerShape(16.dp),
        small = RoundedCornerShape(12.dp),
    )
}

data class AppShapes(
    val large: Shape,
    val medium: Shape,
    val small: Shape,
)