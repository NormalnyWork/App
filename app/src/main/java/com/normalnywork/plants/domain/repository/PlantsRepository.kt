package com.normalnywork.plants.domain.repository

import com.normalnywork.plants.domain.entity.Plant

interface PlantsRepository {

    suspend fun getPlants(): List<Plant>

    suspend fun createPlant(plant: Plant)
}