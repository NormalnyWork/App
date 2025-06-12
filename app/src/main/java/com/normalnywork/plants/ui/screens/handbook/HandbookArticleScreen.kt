package com.normalnywork.plants.ui.screens.handbook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.normalnywork.plants.ui.kit.common.CareOverview
import com.normalnywork.plants.ui.kit.components.AppTopBar
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.kit.style.LocalAppShapes
import com.normalnywork.plants.ui.kit.style.LocalAppTypography
import com.normalnywork.plants.ui.navigation.screen.HandbookArticleComponent

@Composable
fun HandbookArticleScreen(component: HandbookArticleComponent) {
    val guide = component.guide
    val state = rememberScrollState()

    Column {
        AppTopBar(
            title = guide.name,
            onBack = component::goBack,
            showDivider = state.canScrollBackward
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(guide.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(LocalAppShapes.current.extraSmall),
                contentScale = ContentScale.FillWidth
            )
            CareOverview(
                watering = guide.watering,
                trim = guide.trim,
                rotation = guide.rotation,
                fertilization = guide.fertilization,
                cleaning = guide.cleaning,
                transplantation = guide.transplantation
            )
            HorizontalDivider(color = LocalAppColors.current.strokeSecondary)
            Text(
                text = guide.info,
                style = LocalAppTypography.current.body1,
                color = LocalAppColors.current.textPrimary,
            )
        }
    }
}