package com.normalnywork.plants.data.api.services

import com.normalnywork.plants.data.api.ApiHeaders
import com.normalnywork.plants.data.api.ApiRoutes
import com.normalnywork.plants.data.api.models.TaskDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TasksService {

    @GET(ApiRoutes.GET_TASKS)
    @Headers(ApiHeaders.REQUIRES_AUTHORIZATION)
    suspend fun getTodayTasks(): List<TaskDto>

    @PUT(ApiRoutes.COMPLETE_TASK)
    @Headers(ApiHeaders.REQUIRES_AUTHORIZATION)
    suspend fun completeTask(
        @Path(ApiRoutes.ARGUMENT_1) taskId: Int,
        @Query("status") status: TaskDto.StatusDto = TaskDto.StatusDto.COMPLETED,
    )

    @POST(ApiRoutes.SNOOZE_TASK)
    @Headers(ApiHeaders.REQUIRES_AUTHORIZATION)
    suspend fun snoozeTask(@Path(ApiRoutes.ARGUMENT_1) taskId: Int, )
}