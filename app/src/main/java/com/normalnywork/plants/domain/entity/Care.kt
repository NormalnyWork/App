package com.normalnywork.plants.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Care(
    val interval: CareInterval,
    val count: Int,
)
