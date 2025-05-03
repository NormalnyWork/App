package com.normalnywork.plants.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Error(
    @SerialName("detail") val message: String,
) {
    companion object {

        const val EMAIL_ALREADY_USED = "email_already_registered"
        const val NAME_ALREADY_USED = "name_already_registered"
        const val INVALID_CREDENTIALS = "incorrect_username_or_password"
    }
}