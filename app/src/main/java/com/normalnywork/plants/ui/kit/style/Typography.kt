package com.normalnywork.plants.ui.kit.style

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.normalnywork.plants.R

val LocalAppTypography = staticCompositionLocalOf<AppTypography> { provideAppTypography() }

private val onestFontFamily = FontFamily(
    Font(R.font.onest_regular, weight = FontWeight.Normal),
    Font(R.font.onest_medium, weight = FontWeight.Medium),
    Font(R.font.onest_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.onest_bold, weight = FontWeight.Bold),
)

private fun provideAppTypography(): AppTypography {
    return AppTypography(
        heading1 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
        ),
        heading2 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
        ),
        heading3 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
        ),
        body1 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        ),
        body2 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        ),
        button = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        ),
    )
}

data class AppTypography(
    val heading1: TextStyle,
    val heading2: TextStyle,
    val heading3: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val button: TextStyle,
)