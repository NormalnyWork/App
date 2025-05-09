package com.normalnywork.plants.data.repository

import com.normalnywork.plants.data.api.mappers.toModel
import com.normalnywork.plants.data.api.services.TasksService
import com.normalnywork.plants.domain.entity.Task
import com.normalnywork.plants.domain.repository.TasksRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TasksRepositoryImpl : TasksRepository, KoinComponent {

    private val tasksService: TasksService by inject()

    override suspend fun getTodayTasks(): List<Task> {
        val response = tasksService.getTodayTasks()
        return response.map { it.toModel() }
    }

    override suspend fun markAsCompleted(taskId: Int) {
        tasksService.completeTask(taskId)
    }

    override suspend fun snoozeTask(taskId: Int) {
        tasksService.snoozeTask(taskId)
    }
}