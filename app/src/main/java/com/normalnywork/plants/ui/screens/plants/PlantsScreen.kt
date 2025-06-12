package com.normalnywork.plants.ui.screens.plants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.normalnywork.plants.R
import com.normalnywork.plants.domain.entity.Plant
import com.normalnywork.plants.ui.kit.common.CareOverview
import com.normalnywork.plants.ui.kit.components.AppTopBar
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.kit.style.LocalAppShapes
import com.normalnywork.plants.ui.kit.style.LocalAppTypography
import com.normalnywork.plants.ui.navigation.screen.PlantsComponent

@Composable
fun PlantsScreen(component: PlantsComponent) {
    Scaffold(
        topBar = {
            AppTopBar(title = stringResource(R.string.plants_title))
        },
        content = { contentPaddings ->
            val plants by component.plants.collectAsState()
            val isLoading by component.isLoading.collectAsState()

            PlantsContent(
                contentPaddings = contentPaddings,
                isLoading = isLoading,
                plants = plants,
                editPlant = component::editPlant,
            )
        },
        floatingActionButton = {
            AddFloatingButton(onClick = component::createPlant)
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = LocalAppColors.current.background,
        contentWindowInsets = WindowInsets.statusBars,
    )
}

@Composable
private fun AddFloatingButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(LocalAppShapes.current.extraLarge)
            .background(LocalAppColors.current.primary)
            .clickable(
                role = Role.Button,
                onClick = onClick,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add_circle),
            contentDescription = null,
            tint = LocalAppColors.current.background,
        )
        Text(
            text = stringResource(R.string.plants_add),
            style = LocalAppTypography.current.button2,
            color = LocalAppColors.current.background,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun PlantsContent(
    contentPaddings: PaddingValues,
    isLoading: Boolean,
    plants: List<Plant>,
    editPlant: (Plant) -> Unit,
) {
    when {
        isLoading && plants.isEmpty() -> PlantsLoading(contentPaddings)
        !isLoading && plants.isEmpty() -> PlantsEmpty(contentPaddings)
        else -> PlantsList(
            contentPaddings = contentPaddings,
            plants = plants,
            editPlant = editPlant
        )
    }
}

@Composable
private fun PlantsEmpty(contentPaddings: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPaddings)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_plant_pot),
            contentDescription = null,
            tint = LocalAppColors.current.primary,
            modifier = Modifier.size(56.dp),
        )
        Text(
            text = stringResource(R.string.plants_empty_title),
            style = LocalAppTypography.current.heading2,
            color = LocalAppColors.current.textPrimary,
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(R.string.plants_empty_body),
            style = LocalAppTypography.current.body1,
            color = LocalAppColors.current.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun PlantsLoading(contentPaddings: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPaddings)
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

@Composable
private fun PlantsList(
    contentPaddings: PaddingValues,
    plants: List<Plant>,
    editPlant: (Plant) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = contentPaddings.calculateTopPadding()),
        contentPadding = PaddingValues(
            top = 12.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = contentPaddings.calculateBottomPadding() + 80.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(plants, key = { it.id }) { plant ->
            PlantCard(
                plant = plant,
                onEdit = { editPlant(plant) },
                modifier = Modifier.animateItem(),
            )
        }
    }
}

@Composable
private fun PlantCard(
    plant: Plant,
    onEdit: () -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
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
                onClick = onEdit,
            )
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(plant.image)
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
                text = plant.name,
                style = LocalAppTypography.current.heading3,
                color = LocalAppColors.current.textPrimary,
            )
            CareOverview(
                watering = plant.watering,
                trim = plant.trim,
                rotation = plant.rotation,
                fertilization = plant.fertilization,
                cleaning = plant.cleaning,
                transplantation = plant.transplantation
            )
        }
        Icon(
            painter = painterResource(R.drawable.ic_edit),
            contentDescription = stringResource(R.string.plants_edit),
            tint = LocalAppColors.current.primary,
        )
    }
}