package com.normalnywork.plants.ui.kit.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun AppTopBar(title: String) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = title,
            style = LocalAppTypography.current.heading2,
            color = LocalAppColors.current.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}