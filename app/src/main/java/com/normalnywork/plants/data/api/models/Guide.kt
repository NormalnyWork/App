package com.normalnywork.plants.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuideDto(
    @SerialName("name")
    val name: String,
    @SerialName("info")
    val info: String,
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