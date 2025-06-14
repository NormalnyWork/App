package com.normalnywork.plants.ui.navigation.impl.content

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.normalnywork.plants.R
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.kit.style.LocalAppTypography
import com.normalnywork.plants.ui.navigation.flow.BottomNavigationComponent
import com.normalnywork.plants.ui.navigation.flow.BottomNavigationComponent.BottomNavigationConfig
import com.normalnywork.plants.ui.navigation.flow.BottomNavigationComponent.BottomNavigationScreen
import com.normalnywork.plants.ui.screens.plants.PlantsScreen
import com.normalnywork.plants.ui.screens.tasks.TasksScreen

val LocalBottomBarHeight = compositionLocalOf { 0.dp }

@Composable
fun BottomNavigationContent(component: BottomNavigationComponent) {
    Children(
        stack = component.childStack,
        modifier = Modifier.fillMaxSize(),
    ) { child ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalAppColors.current.background),
        ) {
            var bottomBarHeight by remember { mutableStateOf(0.dp) }

            CompositionLocalProvider(LocalBottomBarHeight provides bottomBarHeight) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    when (val screen = child.instance) {
                        is BottomNavigationScreen.TasksScreen -> TasksScreen(screen.component)
                        is BottomNavigationScreen.PlantsScreen -> PlantsScreen(screen.component)
                        is BottomNavigationScreen.HandbookScreen -> HandbookNavigationContent(screen.component)
                    }
                }
            }
            HorizontalDivider(color = LocalAppColors.current.strokeSecondary)
            BottomNavBar(
                currentConfiguration = child.configuration as BottomNavigationConfig,
                component = component,
                onHeightChanged = { bottomBarHeight = it }
            )
        }
    }
    RequireNotificationsPermission()
}

@Composable
private fun BottomNavBar(
    currentConfiguration: BottomNavigationConfig,
    component: BottomNavigationComponent,
    onHeightChanged: (Dp) -> Unit
) {
    val density = LocalDensity.current

    NavigationBar(
        containerColor = LocalAppColors.current.background,
        modifier = Modifier
            .onSizeChanged { size ->
                onHeightChanged(with(density) { size.height.toDp() })
            }
    ) {
        listOf(
            BottomNavigationConfig.Tasks,
            BottomNavigationConfig.Plants,
            BottomNavigationConfig.Handbook,
        ).forEach { tab ->
            val selected = currentConfiguration == tab

            NavigationBarItem(
                selected = selected,
                onClick = { component.selectNewTab(tab) },
                label = {
                    Text(
                        text = stringResource(tab.tabName()),
                        style = if (selected) {
                            LocalAppTypography.current.caption2
                        } else {
                            LocalAppTypography.current.caption3
                        },
                        color = LocalContentColor.current,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(tab.tabIcon(selected)),
                        contentDescription = null,
                        tint = LocalContentColor.current,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LocalAppColors.current.primary,
                    selectedTextColor = LocalAppColors.current.primary,
                    indicatorColor = LocalAppColors.current.primary.copy(alpha = 0.15f),
                    unselectedIconColor = LocalAppColors.current.textSecondary,
                    unselectedTextColor = LocalAppColors.current.textSecondary,
                ),
            )
        }
    }
}

@Composable
private fun RequireNotificationsPermission() {
    val contract = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        // TODO: show rationale
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            contract.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

private fun BottomNavigationConfig.tabIcon(selected: Boolean) = if (selected) when (this) {
    BottomNavigationConfig.Tasks -> R.drawable.ic_tab_home_selected
    BottomNavigationConfig.Plants -> R.drawable.ic_tab_plants_selected
    BottomNavigationConfig.Handbook -> R.drawable.ic_tab_handbook_selected
} else when (this) {
    BottomNavigationConfig.Tasks -> R.drawable.ic_tab_home_unselected
    BottomNavigationConfig.Plants -> R.drawable.ic_tab_plants_unselected
    BottomNavigationConfig.Handbook -> R.drawable.ic_tab_handbook_unselected
}

private fun BottomNavigationConfig.tabName() = when (this) {
    BottomNavigationConfig.Tasks -> R.string.common_tab_tasks
    BottomNavigationConfig.Plants -> R.string.common_tab_plants
    BottomNavigationConfig.Handbook -> R.string.common_tab_handbook
}