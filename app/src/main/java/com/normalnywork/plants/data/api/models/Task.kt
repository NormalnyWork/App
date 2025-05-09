package com.normalnywork.plants.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    @SerialName("id")
    val id: Int,
    @SerialName("care_type")
    val careType: CareTypeDto,
    @SerialName("scheduled_at")
    val scheduledAt: Long,
    @SerialName("status")
    val status: StatusDto,
    @SerialName("plant_name")
    val plantName: String,
    @SerialName("plant_image")
    val plantImage: String,
) {

    enum class StatusDto {
        PENDING,
        COMPLETED,
        OVERDUE,
    }
}

enum class CareTypeDto {
    WATERING,
    HAIRCUT,
    FERTILIZE,
    ROTATION,
    CLEANING,
    TRANSPLANTATION,
}