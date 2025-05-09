package com.normalnywork.plants.ui.screens.tasks

import android.text.format.DateFormat
import android.text.format.DateUtils
import android.text.format.DateUtils.DAY_IN_MILLIS
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.normalnywork.plants.R
import com.normalnywork.plants.domain.entity.CareType
import com.normalnywork.plants.domain.entity.Task
import com.normalnywork.plants.ui.kit.components.AppTopBar
import com.normalnywork.plants.ui.kit.components.AppVerticalButton
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.kit.style.LocalAppShapes
import com.normalnywork.plants.ui.kit.style.LocalAppTypography
import com.normalnywork.plants.ui.navigation.screen.TasksComponent
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource

@Composable
fun TasksScreen(component: TasksComponent) {
    Column {
        val scrollState = rememberLazyListState()

        AppTopBar(
            title = stringResource(R.string.tasks_title),
            subtitle = remember {
                DateFormat.format("dd.MM.yyyy", System.currentTimeMillis()).toString()
            },
            showDivider = scrollState.canScrollBackward,
        )

        val isLoading by component.isLoading.collectAsState()

        if (isLoading) {
            Loading()
        } else {
            val currentTask by component.currentTask.collectAsState()

            TaskHistoryTransition(currentTask) { showHistory ->
                if (showHistory) {
                    val doneTasks by component.doneTasks.collectAsState()

                    PullToRefreshTasks(component::refresh) {
                        if (doneTasks.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                contentAlignment = Alignment.Center,
                            ) {
                                NoTasksCard()
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp,
                                ),
                                state = scrollState,
                            ) {
                                item { NoTasksCard() }
                                item { DoneTasksSubtitleCard() }
                                items(doneTasks) { TaskHistoryCard(it) }
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxSize(),
                    ) {
                        TaskTransition(currentTask) {
                            TaskCard(it)
                        }
                        TaskActions(
                            onSnooze = component::snooze,
                            onComplete = component::complete,
                            onNext = component::next,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshTasks(
    onRefresh: () -> Unit,
    content: @Composable () -> Unit,
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = false,
        onRefresh = onRefresh,
        state = state,
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = state,
                isRefreshing = false,
                modifier = Modifier.align(Alignment.TopCenter),
                containerColor = LocalAppColors.current.strokeSecondary,
                color = LocalAppColors.current.primary,
            )
        }
    ) { content() }
}

@Composable
private fun NoTasksCard() {
    Column(
        modifier = Modifier
            .padding(vertical = 56.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_check_circle_big),
            contentDescription = null,
            tint = LocalAppColors.current.primary,
        )
        Text(
            text = stringResource(R.string.tasks_no_tasks_subtitle),
            style = LocalAppTypography.current.heading3,
            color = LocalAppColors.current.textPrimary,
        )
    }
}

@Composable
private fun DoneTasksSubtitleCard() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        HorizontalDivider(color = LocalAppColors.current.strokeSecondary)
        Text(
            text = stringResource(R.string.tasks_done_subtitle),
            style = LocalAppTypography.current.heading5,
            color = LocalAppColors.current.textSecondary,
        )
    }
}

@Composable
private fun ColumnScope.TaskTransition(
    currentTask: Task?,
    content: @Composable (Task) -> Unit,
) {
    AnimatedContent(
        targetState = currentTask,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(horizontal = 16.dp),
        transitionSpec = {
            (fadeIn(
                animationSpec = tween()
            ) + slideInHorizontally(
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                initialOffsetX = { it }
            ) togetherWith fadeOut() + scaleOut(targetScale = 0.5f))
                .using(SizeTransform(clip = false))
        }
    ) {
        if (it != null) content(it) else Spacer(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun ColumnScope.TaskHistoryTransition(
    currentTask: Task?,
    content: @Composable (showHistory: Boolean) -> Unit,
) {
    AnimatedContent(
        targetState = currentTask == null,
        modifier = Modifier.fillMaxSize(),
        transitionSpec = {
            (slideInVertically(
                animationSpec = spring(stiffness = 100f, dampingRatio = 0.9f),
                initialOffsetY = { it }
            ) togetherWith fadeOut() + scaleOut(targetScale = 0.9f))
                .using(SizeTransform(clip = false))
        }
    ) { content(it) }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
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
private fun TaskCard(task: Task) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(
                width = 1.dp,
                shape = LocalAppShapes.current.medium,
                color = LocalAppColors.current.primary,
            )
            .clip(LocalAppShapes.current.medium),
        contentAlignment = Alignment.BottomCenter,
    ) {
        val blurState = remember { HazeState() }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(task.plantImage)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(blurState),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .hazeEffect(
                    state = blurState,
                    style = HazeStyle(
                        backgroundColor = LocalAppColors.current.background.copy(alpha = 0.85f),
                        tint = HazeTint(LocalAppColors.current.background.copy(alpha = 0.85f)),
                        blurRadius = 16.dp,
                        noiseFactor = 0f,
                    )
                ) {
                    mask = Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.28f to Color.Black,
                        1f to Color.Black,
                    )
                }
                .padding(top = 56.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = task.scheduledAt.displayTime(),
                style = LocalAppTypography.current.caption1,
                color = if (task.status == Task.Status.OVERDUE) {
                    LocalAppColors.current.error
                } else {
                    LocalAppColors.current.textSecondary
                },
            )
            Text(
                text = task.careType.displayAction(),
                style = LocalAppTypography.current.heading2,
                color = LocalAppColors.current.textPrimary,
            )
            Text(
                text = task.plantName,
                style = LocalAppTypography.current.body1,
                color = LocalAppColors.current.textSecondary,
            )
        }
    }
}

@Composable
private fun TaskActions(
    onSnooze: () -> Unit,
    onComplete: () -> Unit,
    onNext: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        AppVerticalButton(
            text = stringResource(R.string.tasks_action_snooze),
            icon = painterResource(R.drawable.ic_snooze),
            onClick = onSnooze,
            filled = false,
        )
        AppVerticalButton(
            text = stringResource(R.string.tasks_action_complete),
            icon = painterResource(R.drawable.ic_check_circle),
            onClick = onComplete,
            filled = true,
        )
        AppVerticalButton(
            text = stringResource(R.string.tasks_action_next),
            icon = painterResource(R.drawable.ic_next),
            onClick = onNext,
            filled = false,
        )
    }
}

@Composable
private fun TaskHistoryCard(task: Task) {
    Row(
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = LocalAppColors.current.stroke,
                shape = LocalAppShapes.current.medium,
            )
            .clip(LocalAppShapes.current.medium)
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(task.plantImage)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(LocalAppShapes.current.extraSmall),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = task.scheduledAt.displayTime(),
                style = LocalAppTypography.current.caption4,
                color = LocalAppColors.current.textSecondary,
            )
            Text(
                text = task.careType.displayAction(),
                style = LocalAppTypography.current.heading5,
                color = LocalAppColors.current.textPrimary,
            )
            Text(
                text = task.plantName,
                style = LocalAppTypography.current.body3,
                color = LocalAppColors.current.textSecondary,
            )
        }
    }
}

@Composable
private fun Long.displayTime() = when {
    DateUtils.isToday(this) -> stringResource(R.string.tasks_time_today)
    DateUtils.isToday(this + DAY_IN_MILLIS) -> stringResource(R.string.tasks_time_yesterday)
    else -> DateFormat.format("dd.MM", this).toString()
}

@Composable
private fun CareType.displayAction() = when (this) {
    CareType.WATERING -> R.string.tasks_care_watering
    CareType.TRIMMING -> R.string.tasks_care_trim
    CareType.ROTATION -> R.string.tasks_care_rotate
    CareType.FERTILIZATION -> R.string.tasks_care_fertilize
    CareType.CLEANING -> R.string.tasks_care_clean
    CareType.TRANSPLANTATION -> R.string.tasks_care_transplant
}.let { stringResource(it) }