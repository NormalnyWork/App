package com.normalnywork.plants.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import kotlin.math.max

@Composable
fun keyboardAsState(): State<Boolean> {
    var peak by remember { mutableIntStateOf(-1) }
    var lastValue by remember { mutableIntStateOf(-1) }

    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current).also { peak = max(peak, it) }
    val isImeVisible = imeHeight > 0 /*&& (abs(imeHeight - peak) < 10 || imeHeight > lastValue)*/
    lastValue = imeHeight

    return rememberUpdatedState(isImeVisible)
}