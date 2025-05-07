package com.normalnywork.plants.data.api.mappers

import com.normalnywork.plants.data.api.models.CareDto
import com.normalnywork.plants.data.api.models.CareIntervalDto
import com.normalnywork.plants.data.api.models.PlantCreateDto
import com.normalnywork.plants.data.api.models.PlantDto
import com.normalnywork.plants.domain.entity.Care
import com.normalnywork.plants.domain.entity.CareInterval
import com.normalnywork.plants.domain.entity.Plant

fun PlantDto.toModel() = Plant(
    id = id,
    name = name,
    image = image,
    watering = watering?.toModel(),
    trim = trim?.toModel(),
    rotation = rotation?.toModel(),
    cleaning = cleaning?.toModel(),
    transplantation = transplantation?.toModel(),
    fertilization = fertilization?.toModel(),
)

fun Plant.toDto() = PlantDto(
    id = id,
    name = name,
    image = image,
    watering = watering?.toDto(),
    trim = trim?.toDto(),
    rotation = rotation?.toDto(),
    cleaning = cleaning?.toDto(),
    transplantation = transplantation?.toDto(),
    fertilization = fertilization?.toDto()
)

fun Plant.toCreateDto() = PlantCreateDto(
    name = name,
    image = image,
    watering = watering?.toDto(),
    trim = trim?.toDto(),
    rotation = rotation?.toDto(),
    cleaning = cleaning?.toDto(),
    transplantation = transplantation?.toDto(),
)

private fun Care.toDto() = CareDto(
    interval = interval.toDto(),
    count = count,
)

private fun CareInterval.toDto() = when (this) {
    CareInterval.DAY -> CareIntervalDto.DAY
    CareInterval.WEEK -> CareIntervalDto.WEEK
    CareInterval.MONTH -> CareIntervalDto.MONTH
}

private fun CareDto.toModel() = Care(
    interval = interval.toModel(),
    count = count,
)

private fun CareIntervalDto.toModel() = when (this) {
    CareIntervalDto.DAY -> CareInterval.DAY
    CareIntervalDto.WEEK -> CareInterval.WEEK
    CareIntervalDto.MONTH -> CareInterval.MONTH
}