package com.normalnywork.plants.data.repository

import androidx.core.net.toUri
import com.normalnywork.plants.data.api.mappers.toDto
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
        val uploadedPhoto = uploadImage(plant.image)

        plantsService.createPlant(
            plant
                .copy(image = uploadedPhoto)
                .toDto()
        )
    }

    override suspend fun editPlant(plant: Plant) {
        val photo = plant.image
            .takeIf { it.toUri().scheme != "content" }
            ?: uploadImage(plant.image)

        plantsService.editPlant(
            plantId = plant.id,
            plant = plant
                .copy(image = photo)
                .toDto()
        )
    }

    private suspend fun uploadImage(filePath: String): String {
        return plantsService.uploadFile(
            MultipartBody.Part.createFormData(
                name = "file",
                filename = filePath,
                body = FileTools.getFileFromUri(filePath).asRequestBody()
            )
        ).url
    }
}