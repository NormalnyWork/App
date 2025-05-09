package com.normalnywork.plants.domain.repository

import com.normalnywork.plants.domain.entity.Task

interface TasksRepository {

    suspend fun getTodayTasks(): List<Task>

    suspend fun markAsCompleted(taskId: Int)

    suspend fun snoozeTask(taskId: Int)
}