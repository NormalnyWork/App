package com.normalnywork.plants.data.api.services

import com.normalnywork.plants.data.api.ApiRoutes
import com.normalnywork.plants.data.api.models.RegisterFcmTokenRequest
import com.normalnywork.plants.data.api.models.SignInResponse
import com.normalnywork.plants.data.api.models.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {

    @POST(ApiRoutes.SIGN_UP)
    suspend fun signUp(@Body body: SignUpRequest): Int

    @POST(ApiRoutes.SIGN_IN)
    @FormUrlEncoded
    suspend fun signIn(
        @Field("username") email: String,
        @Field("password") password: String,
    ): SignInResponse

    @POST(ApiRoutes.REFRESH)
    suspend fun refreshToken(@Header("refresh-token-in") refreshToken:String): SignInResponse

    @PATCH(ApiRoutes.REGISTER_FCM)
    suspend fun registerFcmToken(@Body body: RegisterFcmTokenRequest)
}