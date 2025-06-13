package com.normalnywork.plants.ui.screens.handbook

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.normalnywork.plants.R
import com.normalnywork.plants.domain.entity.Guide
import com.normalnywork.plants.ui.kit.common.CareOverview
import com.normalnywork.plants.ui.kit.components.SearchTextField
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.kit.style.LocalAppShapes
import com.normalnywork.plants.ui.kit.style.LocalAppTypography
import com.normalnywork.plants.ui.navigation.impl.content.LocalBottomBarHeight
import com.normalnywork.plants.ui.navigation.screen.HandbookListComponent

@Composable
fun HandbookListScreen(
    component: HandbookListComponent,
    isInBottomSheet: Boolean = false,
    onDismiss: () -> Unit = { }
) {
    val searchRequest by component.searchRequest.collectAsState()
    val guides by component.guides.collectAsState()
    val state = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = if (isInBottomSheet) {
                    WindowInsets.ime.asPaddingValues().calculateBottomPadding()
                } else {
                    (WindowInsets.ime.asPaddingValues().calculateBottomPadding() - LocalBottomBarHeight.current)
                        .coerceAtLeast(0.dp)
                }
            )
    ) {
        SearchTopBar(
            searchRequest = searchRequest,
            onSearchRequestChanged = component::onSearchRequestChanged,
            showDivider = state.canScrollBackward,
            isInBottomSheet = isInBottomSheet
        )

        when {
            guides.isEmpty() && searchRequest.isEmpty() -> {
                GuidesLoading()
            }
            guides.isEmpty() && searchRequest.isNotEmpty() -> {
                NothingFound()
            }
            guides.isNotEmpty() -> {
                HandbookGuidesList(
                    guides = guides,
                    onGuideClick = {
                        component.goToGuide(it)
                        if (isInBottomSheet) {
                            onDismiss()
                            component.onSearchRequestChanged("")
                        }
                    },
                    state = state,
                    isInBottomSheet = isInBottomSheet
                )
            }
        }
    }

    LaunchedEffect(searchRequest) {
        state.animateScrollToItem(0)
    }
}

@Composable
private fun SearchTopBar(
    searchRequest: String,
    onSearchRequestChanged: (String) -> Unit,
    showDivider: Boolean,
    isInBottomSheet: Boolean
) {
    Column(
        modifier = (if (!isInBottomSheet) Modifier.statusBarsPadding() else Modifier)
            .padding(top = 8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SearchTextField(
            value = searchRequest,
            onValueChange = onSearchRequestChanged,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        HorizontalDivider(
            color = LocalAppColors.current.strokeSecondary,
            modifier = Modifier.alpha(animateFloatAsState(if (showDivider) 1f else 0f).value)
        )
    }
}

@Composable
private fun HandbookGuidesList(
    guides: List<Guide>,
    onGuideClick: (Guide) -> Unit,
    state: LazyListState,
    isInBottomSheet: Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            top = 8.dp,
            bottom = 16.dp + (if (isInBottomSheet) WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() else 0.dp),
            start = 16.dp,
            end = 16.dp,
        ),
        state = state
    ) {
        items(guides, key = { it.name }) {
            GuideItem(
                guide = it,
                isInBottomSheet = isInBottomSheet
            ) {
                keyboardController?.hide()
                onGuideClick(it)
            }
        }
    }
}

@Composable
private fun LazyItemScope.GuideItem(
    guide: Guide,
    isInBottomSheet: Boolean,
    onGuideClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .animateItem()
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = LocalAppColors.current.stroke,
                shape = LocalAppShapes.current.medium,
            )
            .clip(LocalAppShapes.current.medium)
            .clickable(
                role = Role.Button,
                onClickLabel = stringResource(R.string.plants_edit),
                onClick = onGuideClick,
            )
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(guide.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .clip(LocalAppShapes.current.extraSmall),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = guide.name,
                style = LocalAppTypography.current.heading3,
                color = LocalAppColors.current.textPrimary,
            )
            CareOverview(
                watering = guide.watering,
                trim = guide.trim,
                rotation = guide.rotation,
                fertilization = guide.fertilization,
                cleaning = guide.cleaning,
                transplantation = guide.transplantation
            )
            if (!isInBottomSheet) Text(
                text = stringResource(R.string.handbook_guide_details),
                style = LocalAppTypography.current.body2,
                color = LocalAppColors.current.primary,
            )
        }
    }
}

@Composable
private fun NothingFound() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_nothing_found),
            contentDescription = null,
            tint = LocalAppColors.current.primary,
            modifier = Modifier.size(56.dp),
        )
        Text(
            text = stringResource(R.string.handbook_nothing_found_title),
            style = LocalAppTypography.current.heading2,
            color = LocalAppColors.current.textPrimary,
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(R.string.handbook_nothing_found_desc),
            style = LocalAppTypography.current.body1,
            color = LocalAppColors.current.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun GuidesLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            strokeWidth = 4.dp,
            color = LocalAppColors.current.primary,
        )
    }
}