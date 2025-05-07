package com.normalnywork.plants.data.api.models

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlantDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("image")
    val image: String,
    @SerialName("WATERING")
    val watering: CareDto?,
    @SerialName("HAIRCUT")
    val trim: CareDto?,
    @SerialName("ROTATION")
    val rotation: CareDto?,
    @SerialName("CLEANING")
    val cleaning: CareDto?,
    @SerialName("TRANSPLANTATION")
    val transplantation: CareDto?,
    @SerialName("FERTILIZE")
    val fertilization: CareDto?,
)

@Serializable
data class PlantCreateDto(
    @SerialName("name")
    val name: String,
    @SerialName("image")
    val image: String,
    @SerialName("WATERING")
    val watering: CareDto?,
    @SerialName("HAIRCUT")
    val trim: CareDto?,
    @SerialName("ROTATION")
    val rotation: CareDto?,
    @SerialName("CLEANING")
    val cleaning: CareDto?,
    @SerialName("TRANSPLANTATION")
    val transplantation: CareDto?,
    @SerialName("FERTILIZE")
    val fertilization: CareDto?,
)

@Serializable
data class CareDto(
    @SerialName("interval")
    val interval: CareIntervalDto,
    @SerialName("count")
    val count: Int,
)

@Keep
enum class CareIntervalDto {
    DAY,
    WEEK,
    MONTH,
}