package com.normalnywork.plants.data.api.services

import com.normalnywork.plants.data.api.ApiHeaders
import com.normalnywork.plants.data.api.ApiRoutes
import com.normalnywork.plants.data.api.models.PlantDto
import retrofit2.http.GET
import retrofit2.http.Headers

interface PlantsService {

    @GET(ApiRoutes.GET_PLANTS)
    @Headers(ApiHeaders.REQUIRES_AUTHORIZATION)
    suspend fun getPlants(): List<PlantDto>
}