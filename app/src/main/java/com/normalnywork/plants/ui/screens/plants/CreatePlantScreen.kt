package com.normalnywork.plants.ui.screens.plants

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.normalnywork.plants.R
import com.normalnywork.plants.domain.entity.Care
import com.normalnywork.plants.domain.entity.CareInterval
import com.normalnywork.plants.ui.kit.components.AppPrimaryButton
import com.normalnywork.plants.ui.kit.components.AppSecondaryButton
import com.normalnywork.plants.ui.kit.components.AppTextField
import com.normalnywork.plants.ui.kit.components.AppTopBar
import com.normalnywork.plants.ui.kit.components.dashedBorder
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.kit.style.LocalAppShapes
import com.normalnywork.plants.ui.kit.style.LocalAppTypography
import com.normalnywork.plants.ui.navigation.screen.CreatePlantComponent

@Composable
fun CreatePlantScreen(component: CreatePlantComponent) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            val showDivider by remember { derivedStateOf { scrollState.value > 0 } }

            AppTopBar(
                title = stringResource(R.string.plants_new_title),
                onBack = component::navigateBack,
                showDivider = showDivider,
            )
        },
        bottomBar = {
            val canCreate by component.canCreate.collectAsState()
            val loading by component.isLoading.collectAsState()

            CreateButton(
                enabled = canCreate,
                loading = loading,
                onClick = component::create,
            )
        },
        containerColor = LocalAppColors.current.background,
    ) { contentPaddings ->
        Column(
            modifier = Modifier
                .padding(contentPaddings)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val image by component.image.collectAsState()
            val name by component.name.collectAsState()

            PhotoBlock(
                selectedPhoto = image,
                onPhotoSelected = component::chooseImage
            )
            AppTextField(
                label = stringResource(R.string.plants_new_name_label),
                icon = painterResource(R.drawable.ic_name),
                value = name,
                onValueChange = component::onNameChanged,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            )
            Text(
                text = stringResource(R.string.plants_new_care_title),
                style = LocalAppTypography.current.heading3,
                color = LocalAppColors.current.textPrimary,
                modifier = Modifier.padding(top = 8.dp)
            )
            HandbookButton()
            Text(
                text = stringResource(R.string.plants_new_manual_care_subtitle),
                style = LocalAppTypography.current.heading5,
                color = LocalAppColors.current.textSecondary,
            )

            val watering by component.watering.collectAsState()
            CareCard(
                name = stringResource(R.string.plants_care_watering),
                icon = painterResource(R.drawable.ic_care_watering_outline),
                care = watering,
                toggle = component::toggleWatering,
                onIntervalChanged = component::updateWateringInterval,
                onCountChanged = component::updateWateringCount,
            )
            val trim by component.trim.collectAsState()
            CareCard(
                name = stringResource(R.string.plants_care_trim),
                icon = painterResource(R.drawable.ic_care_trim_outline),
                care = trim,
                toggle = component::toggleTrim,
                onIntervalChanged = component::updateTrimInterval,
                onCountChanged = component::updateTrimCount,
            )
            val fertilization by component.fertilization.collectAsState()
            CareCard(
                name = stringResource(R.string.plants_care_fertilize),
                icon = painterResource(R.drawable.ic_care_fertilization_outline),
                care = fertilization,
                toggle = component::toggleFertilization,
                onIntervalChanged = component::updateFertilizationInterval,
                onCountChanged = component::updateFertilizationCount,
            )
            val cleaning by component.cleaning.collectAsState()
            CareCard(
                name = stringResource(R.string.plants_care_clean),
                icon = painterResource(R.drawable.ic_care_cleaning_outline),
                care = cleaning,
                toggle = component::toggleCleaning,
                onIntervalChanged = component::updateCleaningInterval,
                onCountChanged = component::updateCleaningCount,
            )
            val rotation by component.rotation.collectAsState()
            CareCard(
                name = stringResource(R.string.plants_care_rotate),
                icon = painterResource(R.drawable.ic_care_rotation_outline),
                care = rotation,
                toggle = component::toggleRotation,
                onIntervalChanged = component::updateRotationInterval,
                onCountChanged = component::updateRotationCount,
            )
            val transplantation by component.transplantation.collectAsState()
            CareCard(
                name = stringResource(R.string.plants_care_transplant),
                icon = painterResource(R.drawable.ic_care_transplantation_outline),
                care = transplantation,
                toggle = component::toggleTransplantation,
                onIntervalChanged = component::updateTransplantationInterval,
                onCountChanged = component::updateTransplantationCount,
            )
        }
    }
}

@Composable
private fun CreateButton(
    enabled: Boolean,
    loading: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(LocalAppColors.current.background)
            .navigationBarsPadding()
    ) {
        HorizontalDivider(color = LocalAppColors.current.strokeSecondary)
        AppPrimaryButton(
            text = stringResource(R.string.plants_new_action_add),
            icon = painterResource(R.drawable.ic_check_circle),
            onClick = onClick,
            modifier = Modifier.padding(16.dp),
            enabled = enabled,
            loading = loading,
        )
    }
}

@Composable
private fun HandbookButton() {
    AppSecondaryButton(
        text = stringResource(R.string.plants_new_from_handbook),
        icon = painterResource(R.drawable.ic_handbook),
        filled = false,
        onClick = { TODO() },
    )
}

@Composable
private fun PhotoBlock(
    selectedPhoto: String?,
    onPhotoSelected: (String) -> Unit,
) {
    val pickMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        uri?.toString()?.let(onPhotoSelected)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(196.dp)
            .then(
                if (selectedPhoto != null) Modifier
                    .border(
                        width = 1.dp,
                        color = LocalAppColors.current.stroke,
                        shape = LocalAppShapes.current.medium,
                    )
                else Modifier
                    .dashedBorder(
                        strokeWidth = 1.dp,
                        color = LocalAppColors.current.stroke,
                        shape = LocalAppShapes.current.medium,
                    )
            )
            .clip(LocalAppShapes.current.medium),
    ) {
        AsyncImage(
            model = selectedPhoto,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            onError = { it.result.throwable.printStackTrace() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalAppColors.current.background.copy(alpha = 0.8f))
                .clickable(
                    role = Role.Button,
                    onClick = { pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) }
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_camera_add),
                contentDescription = null,
                tint = if (selectedPhoto != null) {
                    LocalAppColors.current.textPrimary
                } else {
                    LocalAppColors.current.primary
                },
            )
            Text(
                text = stringResource(
                    if (selectedPhoto == null) R.string.plants_new_action_add_photo
                    else R.string.plants_new_action_change_photo
                ),
                style = LocalAppTypography.current.button1,
                color = LocalAppColors.current.textPrimary,
            )
        }
    }
}

@Composable
private fun CareCard(
    name: String,
    icon: Painter,
    care: Care?,
    toggle: () -> Unit,
    onIntervalChanged: (CareInterval) -> Unit,
    onCountChanged: (Int) -> Unit,
) {
    val enabled = care != null
    val borderColor by animateColorAsState(
        if (enabled) LocalAppColors.current.primary
        else LocalAppColors.current.stroke
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = LocalAppShapes.current.medium,
                color = borderColor,
            )
            .clip(LocalAppShapes.current.medium)
            .padding(12.dp)
    ) {
        CareCardHeader(
            enabled = enabled,
            toggle = toggle,
            icon = icon,
            name = name
        )
        AnimatedContent(
            targetState = care != null,
            transitionSpec = {
                (fadeIn() togetherWith fadeOut()).using(SizeTransform(clip = false))
            }
        ) { visible ->
            if (visible) CareIntervalSelection(
                currentInterval = care?.interval,
                onIntervalChanged = onIntervalChanged
            )
        }
        AnimatedContent(
            targetState = care != null,
            transitionSpec = {
                (fadeIn() togetherWith fadeOut()).using(SizeTransform(clip = false))
            }
        ) { visible ->
            if (visible) CareCountSelection(
                care = care,
                onCountChanged = { onCountChanged(it.coerceAtLeast(1)) }
            )
        }
    }
}

@Composable
private fun CareCountSelection(
    care: Care?,
    onCountChanged: (Int) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
    ) {
        val count = care?.count ?: 1

        IconButton(
            onClick = { onCountChanged(count - 1) },
            modifier = Modifier.size(24.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_minus_circle),
                contentDescription = stringResource(R.string.plants_new_action_decrease),
                tint = LocalAppColors.current.primary,
            )
        }
        Text(
            text = count.toString(),
            style = LocalAppTypography.current.heading3,
            color = LocalAppColors.current.textPrimary,
        )
        IconButton(
            onClick = { onCountChanged(count + 1) },
            modifier = Modifier.size(24.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_plus_circle),
                contentDescription = stringResource(R.string.plants_new_action_increase),
                tint = LocalAppColors.current.primary,
            )
        }
        Text(
            text = buildAnnotatedString {
                append(pluralStringResource(R.plurals.plants_new_care_count, count))
                append(" ")
                withStyle(SpanStyle(color = LocalAppColors.current.primary)) {
                    append(
                        stringResource(
                            when (care?.interval) {
                                CareInterval.DAY -> R.string.plants_care_interval_day
                                CareInterval.WEEK -> R.string.plants_care_interval_week
                                CareInterval.MONTH -> R.string.plants_care_interval_month
                                null -> R.string.plants_care_interval_day
                            }
                        ),
                    )
                }
            },
            style = LocalAppTypography.current.body2,
            color = LocalAppColors.current.textPrimary,
        )
    }
}

@Composable
private fun CareIntervalSelection(
    currentInterval: CareInterval?,
    onIntervalChanged: (CareInterval) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.plants_new_interval),
            style = LocalAppTypography.current.body2,
            color = LocalAppColors.current.textPrimary,
        )
        CareInterval.entries.forEach {
            CareIntervalChip(
                interval = it,
                selected = it == currentInterval,
                onClick = { onIntervalChanged(it) }
            )
        }
    }
}

@Composable
private fun CareIntervalChip(
    selected: Boolean,
    interval: CareInterval,
    onClick: () -> Unit,
) {
    val chipBorder by animateColorAsState(
        if (selected) LocalAppColors.current.primary
        else LocalAppColors.current.stroke
    )
    val chipContent by animateColorAsState(
        if (selected) LocalAppColors.current.primary
        else LocalAppColors.current.textPrimary
    )

    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = chipBorder,
                shape = LocalAppShapes.current.extraSmall,
            )
            .clip(LocalAppShapes.current.extraSmall)
            .clickable(
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
    ) {
        Text(
            text = stringResource(
                when (interval) {
                    CareInterval.DAY -> R.string.plants_new_interval_day
                    CareInterval.WEEK -> R.string.plants_new_interval_week
                    CareInterval.MONTH -> R.string.plants_new_interval_month
                }
            ),
            style = LocalAppTypography.current.body2,
            color = chipContent,
        )
    }
}

@Composable
private fun CareCardHeader(
    enabled: Boolean,
    toggle: () -> Unit,
    icon: Painter,
    name: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val contentColor by animateColorAsState(
            if (enabled) LocalAppColors.current.primary
            else LocalAppColors.current.textSecondary
        )

        Icon(
            painter = icon,
            contentDescription = null,
            tint = contentColor,
        )
        Text(
            text = name,
            style = LocalAppTypography.current.heading4,
            color = contentColor,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Switch(
            checked = enabled,
            onCheckedChange = { toggle() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = LocalAppColors.current.primary,
                checkedBorderColor = LocalAppColors.current.primary,
                checkedTrackColor = LocalAppColors.current.primary.copy(alpha = 0.15f),
                uncheckedThumbColor = LocalAppColors.current.textSecondary,
                uncheckedBorderColor = LocalAppColors.current.textSecondary,
                uncheckedTrackColor = LocalAppColors.current.strokeSecondary,
            ),
        )
    }
}