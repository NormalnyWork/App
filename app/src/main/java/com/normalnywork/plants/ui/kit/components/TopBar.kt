package com.normalnywork.plants.ui.kit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.normalnywork.plants.R
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.kit.style.LocalAppTypography

@Composable
fun AppBrandTopBar(modifier: Modifier = Modifier.statusBarsPadding()) {
    Text(
        text = stringResource(R.string.app_name),
        style = LocalAppTypography.current.heading2,
        color = LocalAppColors.current.primary,
        textAlign = TextAlign.Center,
        modifier = modifier
            .padding(top = 24.dp)
            .fillMaxWidth(),
    )
}

@Composable
fun AppTopBar(
    title: String,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null,
    showDivider: Boolean = false,
) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(64.dp),
    ) {
        onBack?.let {
            IconButton(
                onClick = it,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(56.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.common_tab_tasks),
                    tint = LocalAppColors.current.primary,
                )
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = if (onBack == null) 16.dp else 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = title,
                style = LocalAppTypography.current.heading2,
                color = LocalAppColors.current.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            subtitle?.let {
                Text(
                    text = it,
                    style = LocalAppTypography.current.body3,
                    color = LocalAppColors.current.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        if (showDivider) HorizontalDivider(
            color = LocalAppColors.current.strokeSecondary,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}