package com.normalnywork.plants.ui.screens.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.normalnywork.plants.R
import com.normalnywork.plants.ui.kit.components.AppBrandTopBar
import com.normalnywork.plants.ui.kit.components.AppPrimaryButton
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.kit.style.LocalAppTypography
import com.normalnywork.plants.ui.navigation.screen.OnboardingComponent

@Composable
fun OnboardingScreen(component: OnboardingComponent) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        val page by component.currentPage.collectAsState()

        AppBrandTopBar()
        OnboardingContent(page)
        ActionButton(
            text = page.buttonText(),
            onClick = component::next
        )
    }
}

@Composable
private fun OnboardingContent(page: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        OnboardingPageContentTransition(page) { currentPage ->
            Image(
                painter = currentPage.image(),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillBounds
            )
        }
        OnboardingPageContentTransition(page) { currentPage ->
            Text(
                text = currentPage.title(),
                style = LocalAppTypography.current.heading1,
                color = LocalAppColors.current.textPrimary,
            )
        }
        OnboardingPageContentTransition(page) { currentPage ->
            Text(
                text = currentPage.content(),
                style = LocalAppTypography.current.body1,
                color = LocalAppColors.current.textSecondary,
            )
        }
    }
}

@Composable
private fun OnboardingPageContentTransition(
    page: Int,
    content: @Composable (Int) -> Unit
) {
    AnimatedContent(
        targetState = page,
        transitionSpec = pageContentTransition()
    ) {
        content(it)
    }
}

@Composable
private fun ActionButton(
    text: String,
    onClick: () -> Unit,
) {
    AppPrimaryButton(
        text = text,
        onClick = onClick,
        modifier = Modifier
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
            .padding(bottom = 24.dp),
    )
}

@Composable
private fun Int.buttonText() = if (this >= 2) {
    R.string.onboarding_action_finish
} else {
    R.string.onboarding_action_next
}.let { stringResource(it) }

@Composable
private fun Int.image() = when (this) {
    0 -> R.drawable.img_onboarding_1
    1 -> R.drawable.img_onboarding_2
    2 -> R.drawable.img_onboarding_3
    else -> error("Invalid page index: $this")
}.let { painterResource(it) }

@Composable
private fun Int.title() = when (this) {
    0 -> R.string.onboarding_title_1
    1 -> R.string.onboarding_title_2
    2 -> R.string.onboarding_title_3
    else -> error("Invalid page index: $this")
}.let { stringResource(it) }

@Composable
private fun Int.content() = when (this) {
    0 -> R.string.onboarding_content_1
    1 -> R.string.onboarding_content_2
    2 -> R.string.onboarding_content_3
    else -> error("Invalid page index: $this")
}.let { stringResource(it) }

@Composable
private fun pageContentTransition(): AnimatedContentTransitionScope<Int>.() -> ContentTransform = {
    val direction = if (targetState > initialState) 1 else -1

    (fadeIn() + slideInHorizontally(
        animationSpec = spring(),
        initialOffsetX = { it / 2 * direction },
    ) togetherWith fadeOut() + slideOutHorizontally(
        animationSpec = spring(),
        targetOffsetX = { -it / 2 * direction },
    )).using(SizeTransform(clip = false))
}