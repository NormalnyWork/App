package com.normalnywork.plants.data.api.services

import com.normalnywork.plants.data.api.ApiHeaders
import com.normalnywork.plants.data.api.ApiRoutes
import com.normalnywork.plants.data.api.models.PlantCreateDto
import com.normalnywork.plants.data.api.models.PlantDto
import com.normalnywork.plants.data.api.models.UploadedFileDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PlantsService {

    @GET(ApiRoutes.GET_PLANTS)
    @Headers(ApiHeaders.REQUIRES_AUTHORIZATION)
    suspend fun getPlants(): List<PlantDto>

    @POST(ApiRoutes.CREATE_PLANT)
    @Headers(ApiHeaders.REQUIRES_AUTHORIZATION)
    suspend fun createPlant(@Body plant: PlantCreateDto): Int

    @POST(ApiRoutes.UPLOAD_FILE)
    @Headers(ApiHeaders.REQUIRES_AUTHORIZATION)
    @Multipart
    suspend fun uploadFile(@Part filePart: MultipartBody.Part): UploadedFileDto
}