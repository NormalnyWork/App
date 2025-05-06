package com.normalnywork.plants.data.api.mappers

import com.normalnywork.plants.data.api.models.CareDto
import com.normalnywork.plants.data.api.models.CareIntervalDto
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
    fertilization = null, // TODO
)

private fun CareDto.toModel() = Care(
    interval = interval.toModel(),
    count = count,
)

private fun CareIntervalDto.toModel() = when (this) {
    CareIntervalDto.DAY -> CareInterval.DAY
    CareIntervalDto.WEEK -> CareInterval.WEEK
    CareIntervalDto.MONTH -> CareInterval.MONTH
}