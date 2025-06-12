package com.normalnywork.plants.data.api.mappers

import com.normalnywork.plants.data.api.models.GuideDto
import com.normalnywork.plants.domain.entity.Guide

fun GuideDto.toModel() = Guide(
    name = name,
    info = info,
    image = image,
    watering = watering?.toModel(),
    trim = trim?.toModel(),
    rotation = rotation?.toModel(),
    fertilization = fertilization?.toModel(),
    cleaning = cleaning?.toModel(),
    transplantation = transplantation?.toModel(),
)