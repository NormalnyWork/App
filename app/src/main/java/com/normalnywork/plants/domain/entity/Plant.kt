package com.normalnywork.plants.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Plant(
    val id: Int,
    val name: String,
    val image: String,
    val watering: Care?,
    val trim: Care?,
    val rotation: Care?,
    val fertilization: Care?,
    val cleaning: Care?,
    val transplantation: Care?,
)
