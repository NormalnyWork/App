package com.normalnywork.plants.data.api

import com.normalnywork.plants.data.api.models.SignInResponse
import com.normalnywork.plants.data.api.models.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {

    @POST(Routes.SIGN_UP)
    suspend fun signUp(@Body body: SignUpRequest): Int

    @POST(Routes.SIGN_IN)
    @FormUrlEncoded
    suspend fun signIn(
        @Field("username") email: String,
        @Field("password") password: String,
    ): SignInResponse

    @POST(Routes.REFRESH)
    suspend fun refreshToken(@Header("refresh-token") refreshToken:String): SignInResponse
}