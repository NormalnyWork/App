package com.normalnywork.plants.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadedFileDto(
    @SerialName("file_url") val url: String,
)