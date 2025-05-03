package com.normalnywork.plants.data.api

object Routes {

    const val SIGN_UP = "user"
    const val SIGN_IN = "token"
    const val REFRESH = "refresh"
}

object Headers {

    const val AUTHORIZATION = "Authorization"
    const val BEARER_TOKEN = "Bearer-token"
    const val REQUIRES_AUTHORIZATION = "$AUTHORIZATION: true"
}