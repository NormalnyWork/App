package com.normalnywork.plants.ui.kit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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