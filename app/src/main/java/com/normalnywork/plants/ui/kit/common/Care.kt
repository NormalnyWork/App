package com.normalnywork.plants.ui.kit.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.normalnywork.plants.R
import com.normalnywork.plants.domain.entity.Care
import com.normalnywork.plants.domain.entity.CareInterval
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.kit.style.LocalAppTypography

@Composable
fun CareOverview(
    watering: Care?,
    trim: Care?,
    rotation: Care?,
    fertilization: Care?,
    cleaning: Care?,
    transplantation: Care?,
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        watering?.let {
            CarePreview(
                icon = painterResource(R.drawable.ic_care_watering),
                action = stringResource(R.string.plants_care_watering),
                interval = it,
            )
        }
        trim?.let {
            CarePreview(
                icon = painterResource(R.drawable.ic_care_trim),
                action = stringResource(R.string.plants_care_trim),
                interval = it,
            )
        }
        rotation?.let {
            CarePreview(
                icon = painterResource(R.drawable.ic_care_rotation),
                action = stringResource(R.string.plants_care_rotate),
                interval = it,
            )
        }
        fertilization?.let {
            CarePreview(
                icon = painterResource(R.drawable.ic_care_fertilization),
                action = stringResource(R.string.plants_care_fertilize),
                interval = it,
            )
        }
        cleaning?.let {
            CarePreview(
                icon = painterResource(R.drawable.ic_care_cleaning),
                action = stringResource(R.string.plants_care_clean),
                interval = it,
            )
        }
        transplantation?.let {
            CarePreview(
                icon = painterResource(R.drawable.ic_care_transplantation),
                action = stringResource(R.string.plants_care_transplant),
                interval = it,
            )
        }
    }
}

@Composable
fun CarePreview(
    icon: Painter,
    action: String,
    interval: Care,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = LocalAppColors.current.textSecondary,
        )
        Text(
            text = "$action ${
                pluralStringResource(
                    R.plurals.plants_care_count,
                    interval.count,
                    interval.count,
                )
            } ${
                when(interval.interval) {
                    CareInterval.DAY -> stringResource(R.string.plants_care_interval_day)
                    CareInterval.WEEK -> stringResource(R.string.plants_care_interval_week)
                    CareInterval.MONTH -> stringResource(R.string.plants_care_interval_month)
                }
            }",
            style = LocalAppTypography.current.body3,
            color = LocalAppColors.current.textSecondary,
        )
    }
}