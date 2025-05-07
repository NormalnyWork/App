package com.normalnywork.plants.data.repository

import com.normalnywork.plants.data.api.mappers.toCreateDto
import com.normalnywork.plants.data.api.mappers.toModel
import com.normalnywork.plants.data.api.services.PlantsService
import com.normalnywork.plants.domain.entity.Plant
import com.normalnywork.plants.domain.repository.PlantsRepository
import com.normalnywork.plants.utils.FileTools
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlantRepositoryImpl : PlantsRepository, KoinComponent {

    private val plantsService: PlantsService by inject()

    override suspend fun getPlants(): List<Plant> {
        val response = plantsService.getPlants()
        return response.map { it.toModel() }
    }

    override suspend fun createPlant(plant: Plant) {
        val uploadedPhoto = plantsService.uploadFile(
            MultipartBody.Part.createFormData(
                name = "file",
                filename = plant.image,
                body = FileTools.getFileFromUri(plant.image).asRequestBody()
            )
        )
        plantsService.createPlant(
            plant
                .copy(image = uploadedPhoto.url)
                .toCreateDto()
        )
    }
}