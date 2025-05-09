package com.normalnywork.plants.ui.navigation.screen

import com.normalnywork.plants.domain.entity.Task
import kotlinx.coroutines.flow.StateFlow

interface TasksComponent {

    val isLoading: StateFlow<Boolean>
    val currentTask: StateFlow<Task?>
    val doneTasks: StateFlow<List<Task>>

    fun next()
    fun snooze()
    fun complete()

    fun refresh()
}