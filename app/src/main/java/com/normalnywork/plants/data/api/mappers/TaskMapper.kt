package com.normalnywork.plants.data.api.mappers

import com.normalnywork.plants.data.api.models.CareTypeDto
import com.normalnywork.plants.data.api.models.TaskDto
import com.normalnywork.plants.domain.entity.CareType
import com.normalnywork.plants.domain.entity.Task

fun TaskDto.toModel() = Task(
    id = id,
    careType = careType.toModel(),
    scheduledAt = scheduledAt * 1000,
    status = status.toModel(),
    plantName = plantName,
    plantImage = plantImage,
)

private fun CareTypeDto.toModel() = when (this) {
    CareTypeDto.WATERING -> CareType.WATERING
    CareTypeDto.HAIRCUT -> CareType.TRIMMING
    CareTypeDto.FERTILIZE -> CareType.FERTILIZATION
    CareTypeDto.ROTATION -> CareType.ROTATION
    CareTypeDto.CLEANING -> CareType.CLEANING
    CareTypeDto.TRANSPLANTATION -> CareType.TRANSPLANTATION
}

private fun TaskDto.StatusDto.toModel() = when (this) {
    TaskDto.StatusDto.PENDING -> Task.Status.PENDING
    TaskDto.StatusDto.COMPLETED -> Task.Status.COMPLETED
    TaskDto.StatusDto.OVERDUE -> Task.Status.OVERDUE
}