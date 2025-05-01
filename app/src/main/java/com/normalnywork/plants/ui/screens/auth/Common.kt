package com.normalnywork.plants.ui.screens.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import com.normalnywork.plants.ui.kit.components.AppBrandTopBar
import com.normalnywork.plants.ui.kit.components.AppPrimaryButton
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.kit.style.LocalAppTypography
import com.normalnywork.plants.utils.keyboardAsState

@Composable
fun AuthScreenCore(
    title: String,
    question: String,
    switchToOtherScreen: () -> Unit,
    textFields: @Composable ColumnScope.() -> Unit,
    isLoading: Boolean,
    actionText: String,
    onActionClick: () -> Unit,
) {
    Column {
        val keyboardShown by keyboardAsState()

        AuthBrandingTopBar(keyboardShown)
        AuthTopBar(
            title = title,
            question = question.toClickableQuestion(onClick = switchToOtherScreen),
            isCollapsed = keyboardShown
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = textFields,
        )
        AppPrimaryButton(
            text = actionText,
            onClick = onActionClick,
            loading = isLoading,
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 24.dp),
        )
    }
}

@Composable
fun AuthTopBar(
    title: String,
    question: AnnotatedString,
    isCollapsed: Boolean,
) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 32.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val transition by animateFloatAsState(if (isCollapsed) 1f else 0f)
        val titleStyle = lerp(
            start = LocalAppTypography.current.heading1,
            stop = LocalAppTypography.current.heading2,
            fraction = transition,
        )

        Text(
            text = title,
            style = titleStyle,
            color = LocalAppColors.current.textPrimary,
        )
        Text(
            text = question,
            style = LocalAppTypography.current.body2,
            color = LocalAppColors.current.textSecondary,
        )
    }
}

@Composable
fun AuthBrandingTopBar(keyboardShown: Boolean) {
    AnimatedContent(
        targetState = keyboardShown,
        transitionSpec = {
            (fadeIn() + slideInVertically(
                animationSpec = spring(),
                initialOffsetY = { fullHeight -> -fullHeight }
            ) togetherWith fadeOut() + slideOutVertically(
                animationSpec = spring(),
                targetOffsetY = { fullHeight -> -fullHeight }
            )).using(SizeTransform(clip = false))
        },
        modifier = Modifier.statusBarsPadding(),
    ) { isKeyboardShown ->
        if (!isKeyboardShown) AppBrandTopBar(modifier = Modifier.padding(bottom = 48.dp))
    }
}

@Composable
fun String.toClickableQuestion(onClick: () -> Unit) = buildAnnotatedString {
    val startIndex = indexOf('[')
    val endIndex = indexOf(']')

    if (startIndex != -1 && endIndex != -1) {
        val textBefore = substring(0, startIndex)
        val clickableText = substring(startIndex + 1, endIndex)
        val textAfter = substring(endIndex + 1)

        append(textBefore)
        withLink(
            LinkAnnotation.Clickable(
                tag = "clickable",
                styles = TextLinkStyles(
                    style = SpanStyle(color = LocalAppColors.current.primary)
                ),
                linkInteractionListener = { onClick() },
            )
        ) {
            append(clickableText)
        }
        append(textAfter)
    } else {
        append(this@toClickableQuestion)
    }
}