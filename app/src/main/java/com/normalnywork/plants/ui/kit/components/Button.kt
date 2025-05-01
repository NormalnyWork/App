package com.normalnywork.plants.ui.kit.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.kit.style.LocalAppShapes
import com.normalnywork.plants.ui.kit.style.LocalAppTypography

@Composable
fun AppPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = true,
    colors: AppButtonColors = AppButtonColors.Default,
) {
    val background by colors.backgroundColor(enabled = enabled)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(LocalAppShapes.current.large)
            .background(background)
            .clickable(
                enabled = enabled && !loading,
                role = Role.Button,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Crossfade(loading) { isLoading ->
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(28.dp),
                    color = colors.contentColor,
                    strokeWidth = 2.dp,
                )
            } else {
                Text(
                    text = text,
                    style = LocalAppTypography.current.button,
                    color = colors.contentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

data class AppButtonColors(
    val backgroundColor: Color,
    val disabledBackgroundColor: Color,
    val contentColor: Color,
) {

    @Composable
    fun backgroundColor(enabled: Boolean): State<Color> {
        val color = if (enabled) backgroundColor else disabledBackgroundColor
        return animateColorAsState(color)
    }

    companion object {

        val Default: AppButtonColors
            @Composable get() = AppButtonColors(
                backgroundColor = LocalAppColors.current.primary,
                disabledBackgroundColor = LocalAppColors.current.primary.copy(alpha = 0.5f),
                contentColor = LocalAppColors.current.background,
            )
    }
}