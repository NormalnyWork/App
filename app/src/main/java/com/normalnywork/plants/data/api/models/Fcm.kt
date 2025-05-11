package com.normalnywork.plants.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterFcmTokenRequest(
    @SerialName("fcm_token") val fcmToken: String,
    @SerialName("timezone") val timezone: String,
)