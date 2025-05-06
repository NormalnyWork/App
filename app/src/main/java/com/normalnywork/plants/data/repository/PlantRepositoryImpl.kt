package com.normalnywork.plants.data.repository

import com.normalnywork.plants.data.api.mappers.toModel
import com.normalnywork.plants.data.api.services.PlantsService
import com.normalnywork.plants.domain.entity.Plant
import com.normalnywork.plants.domain.repository.PlantsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlantRepositoryImpl : PlantsRepository, KoinComponent {

    private val plantsService: PlantsService by inject()

    override suspend fun getPlants(): List<Plant> {
        val response = plantsService.getPlants()
        return response.map { it.toModel() }
    }

    override suspend fun createPlant(plant: Plant) {
        TODO("Not yet implemented")
    }
}