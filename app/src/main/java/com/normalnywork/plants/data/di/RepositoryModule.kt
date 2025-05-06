package com.normalnywork.plants.data.di

import com.normalnywork.plants.data.repository.AuthRepositoryImpl
import com.normalnywork.plants.data.repository.PlantRepositoryImpl
import com.normalnywork.plants.domain.repository.AppRepository
import com.normalnywork.plants.domain.repository.AuthRepository
import com.normalnywork.plants.domain.repository.PlantsRepository
import com.normalnywork.plants.utils.AppRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<AuthRepository> { AuthRepositoryImpl() }

    single<AppRepository> { AppRepositoryImpl() }

    single<PlantsRepository> { PlantRepositoryImpl() }
}