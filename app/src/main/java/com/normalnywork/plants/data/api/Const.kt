package com.normalnywork.plants.data.api

object ApiRoutes {

    const val ARGUMENT_1 = "argument1"

    const val SIGN_UP = "/user"
    const val SIGN_IN = "/token"
    const val REFRESH = "/refresh"
    const val REGISTER_FCM = "/user"

    const val GET_PLANTS = "/plant"
    const val CREATE_PLANT = "/plant"
    const val EDIT_PLANT = "/plant/{$ARGUMENT_1}"
    const val UPLOAD_FILE = "/file/upload"
    const val GET_GUIDES_LIST = "/plant/guide"

    const val GET_TASKS = "/tasks/today"
    const val COMPLETE_TASK = "/tasks/{$ARGUMENT_1}/status"
    const val SNOOZE_TASK = "/tasks/{$ARGUMENT_1}/postpone"
}

object ApiHeaders {

    const val AUTHORIZATION = "Authorization"
    const val BEARER_TOKEN = "Bearer-token"
    const val REQUIRES_AUTHORIZATION = "$AUTHORIZATION: true"
}