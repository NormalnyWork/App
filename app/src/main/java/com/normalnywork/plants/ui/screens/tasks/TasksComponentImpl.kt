package com.normalnywork.plants.ui.screens.tasks

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.normalnywork.plants.domain.entity.Task
import com.normalnywork.plants.domain.repository.TasksRepository
import com.normalnywork.plants.ui.navigation.screen.TasksComponent
import com.normalnywork.plants.utils.componentScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.LinkedList
import java.util.Queue

class TasksComponentImpl(
    componentContext: ComponentContext,
) : TasksComponent, ComponentContext by componentContext, KoinComponent {

    private val tasksRepository: TasksRepository by inject()

    private val stateHolder = instanceKeeper.getOrCreate { StateHolder() }

    override val isLoading = MutableStateFlow(false)
    override val currentTask = MutableStateFlow(stateHolder.activeTasks.peek())
    override val doneTasks = MutableStateFlow(stateHolder.doneTasks)

    init { loadTasks() }

    override fun next() {
        val currentTask = currentTask.value ?: return

        stateHolder.activeTasks.run {
            remove(currentTask)
            add(currentTask)
        }
        updateUi()
    }

    override fun snooze() {
        val currentTask = currentTask.value ?: return

        componentScope.launch { tasksRepository.snoozeTask(currentTask.id) }
        stateHolder.activeTasks.remove(currentTask)
        updateUi()
    }

    override fun complete() {
        val currentTask = currentTask.value ?: return

        componentScope.launch { tasksRepository.markAsCompleted(currentTask.id) }
        stateHolder.doneTasks += currentTask
        stateHolder.activeTasks.remove(currentTask)
        updateUi()
    }

    override fun refresh() {
        stateHolder.activeTasks.clear()
        stateHolder.doneTasks = emptyList()
        updateUi()

        loadTasks()
    }

    private fun loadTasks() {
        componentScope.launch {
            runCatching {
                isLoading.value = true
                tasksRepository.getTodayTasks()
            }.onSuccess {
                stateHolder.doneTasks = it.filter { it.status == Task.Status.COMPLETED }
                it.filter { it.status == Task.Status.PENDING }
                    .forEach { stateHolder.activeTasks.add(it) }

                updateUi()
            }.onFailure {
                Log.e("TasksComponentImpl", "Error loading tasks", it)
            }.also {
                isLoading.value = false
            }
        }
    }

    private fun updateUi() {
        currentTask.value = stateHolder.activeTasks.peek()
        doneTasks.value = stateHolder.doneTasks
    }

    class StateHolder : InstanceKeeper.Instance {

        val activeTasks: Queue<Task> = LinkedList()
        var doneTasks = listOf<Task>()
    }
}