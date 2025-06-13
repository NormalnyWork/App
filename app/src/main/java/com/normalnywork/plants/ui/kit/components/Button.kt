package com.normalnywork.plants.ui.kit.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
    icon: Painter? = null,
    enabled: Boolean = true,
    loading: Boolean = false,
    colors: AppPrimaryButtonColors = AppPrimaryButtonColors.Default,
) {
    val background by colors.backgroundColor(enabled = enabled)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(LocalAppShapes.current.extraLarge)
            .background(background)
            .clickable(
                enabled = enabled && !loading,
                role = Role.Button,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedContent(loading) { isLoading ->
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(28.dp),
                    color = colors.contentColor,
                    strokeWidth = 2.dp,
                )
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = text,
                        style = LocalAppTypography.current.button2,
                        color = colors.contentColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false),
                    )
                    icon?.let {
                        Icon(
                            painter = it,
                            contentDescription = null,
                            tint = colors.contentColor,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    filled: Boolean = true,
    colors: AppSecondaryButtonColors = AppSecondaryButtonColors.Default,
) {
    val background by colors.backgroundColor(filled = filled)
    val stroke by colors.strokeColor(filled = filled)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .dashedBorder(
                strokeWidth = 2.dp,
                color = stroke,
                shape = LocalAppShapes.current.medium,
            )
            .clip(LocalAppShapes.current.medium)
            .background(background)
            .clickable(
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val content by colors.contentColor(filled = filled)

        icon?.let {
            Icon(
                painter = it,
                contentDescription = null,
                tint = content,
            )
        }
        AnimatedContent(
            targetState = text,
            transitionSpec = { fadeIn() togetherWith fadeOut() }
        ) {
            Text(
                text = it,
                style = LocalAppTypography.current.button2,
                color = content,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun AppVerticalButton(
    text: String,
    icon: Painter,
    onClick: () -> Unit,
    filled: Boolean = true,
    colors: AppVerticalButtonColors = AppVerticalButtonColors.Default,
) {
    val background by colors.backgroundColor(filled = filled)
    val stroke by colors.strokeColor(filled = filled)

    Column(
        modifier = Modifier
            .size(96.dp)
            .border(
                width = 1.dp,
                color = stroke,
                shape = LocalAppShapes.current.extraLarge,
            )
            .clip(
                if (filled) LocalAppShapes.current.large
                else LocalAppShapes.current.extraLarge
            )
            .background(background)
            .clickable(
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val iconColor by colors.iconColor(filled = filled)
        val textColor by colors.textColor(filled = filled)

        Icon(
            painter = icon,
            contentDescription = null,
            tint = iconColor,
        )
        Text(
            text = text,
            style = LocalAppTypography.current.button2,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

data class AppPrimaryButtonColors(
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

        val Default: AppPrimaryButtonColors
            @Composable get() = AppPrimaryButtonColors(
                backgroundColor = LocalAppColors.current.primary,
                disabledBackgroundColor = LocalAppColors.current.primary.copy(alpha = 0.5f),
                contentColor = LocalAppColors.current.background,
            )
    }
}

data class AppSecondaryButtonColors(
    val backgroundColor: Color,
    val strokeColor: Color,
    val contentColor: Color,
    val contentColorFilled: Color,
) {

    @Composable
    fun backgroundColor(filled: Boolean): State<Color> {
        val color = if (filled) backgroundColor else Color.Transparent
        return animateColorAsState(color)
    }

    @Composable
    fun strokeColor(filled: Boolean): State<Color> {
        val color = if (!filled) strokeColor else Color.Transparent
        return animateColorAsState(color)
    }

    @Composable
    fun contentColor(filled: Boolean): State<Color> {
        val color = if (filled) contentColorFilled else contentColor
        return animateColorAsState(color)
    }

    companion object {

        val Default: AppSecondaryButtonColors
            @Composable get() = AppSecondaryButtonColors(
                backgroundColor = LocalAppColors.current.primary,
                strokeColor = LocalAppColors.current.primary,
                contentColor = LocalAppColors.current.primary,
                contentColorFilled = LocalAppColors.current.background,
            )
    }
}

data class AppVerticalButtonColors(
    val backgroundColor: Color,
    val strokeColor: Color,
    val textColor: Color,
    val textColorFilled: Color,
    val iconColor: Color,
    val iconColorFilled: Color,
) {

    @Composable
    fun backgroundColor(filled: Boolean): State<Color> {
        val color = if (filled) backgroundColor else Color.Transparent
        return animateColorAsState(color)
    }

    @Composable
    fun strokeColor(filled: Boolean): State<Color> {
        val color = if (!filled) strokeColor else Color.Transparent
        return animateColorAsState(color)
    }

    @Composable
    fun textColor(filled: Boolean): State<Color> {
        val color = if (filled) textColorFilled else textColor
        return animateColorAsState(color)
    }

    @Composable
    fun iconColor(filled: Boolean): State<Color> {
        val color = if (filled) iconColorFilled else iconColor
        return animateColorAsState(color)
    }

    companion object {

        val Default: AppVerticalButtonColors
            @Composable get() = AppVerticalButtonColors(
                backgroundColor = LocalAppColors.current.primary,
                strokeColor = LocalAppColors.current.stroke,
                textColor = LocalAppColors.current.textPrimary,
                textColorFilled = LocalAppColors.current.background,
                iconColor = LocalAppColors.current.primary,
                iconColorFilled = LocalAppColors.current.background,
            )
    }
}