package com.normalnywork.plants.data.api

object ApiRoutes {

    const val SIGN_UP = "/user"
    const val SIGN_IN = "/token"
    const val REFRESH = "/refresh"

    const val GET_PLANTS = "/plant"
    const val CREATE_PLANT = "/plant"
    const val UPLOAD_FILE = "/file/upload"
}

object ApiHeaders {

    const val AUTHORIZATION = "Authorization"
    const val BEARER_TOKEN = "Bearer-token"
    const val REQUIRES_AUTHORIZATION = "$AUTHORIZATION: true"
}