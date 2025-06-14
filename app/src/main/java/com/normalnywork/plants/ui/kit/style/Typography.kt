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
        heading4 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
        ),
        heading5 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        ),
        body1 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        ),
        body2 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        ),
        body3 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        ),
        body4 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
        ),
        caption1 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        ),
        caption2 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        ),
        caption3 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
        ),
        caption4 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
        ),
        button1 = TextStyle(
            fontFamily = onestFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
        ),
        button2 = TextStyle(
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
    val heading4: TextStyle,
    val heading5: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
    val body4: TextStyle,
    val caption1: TextStyle,
    val caption2: TextStyle,
    val caption3: TextStyle,
    val caption4: TextStyle,
    val button1: TextStyle,
    val button2: TextStyle,
)