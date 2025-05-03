package com.normalnywork.plants.domain.repository

import com.normalnywork.plants.domain.entity.AuthError

interface AuthRepository {

    suspend fun signUp(
        username: String,
        email: String,
        password: String,
    ): AuthError?

    suspend fun signIn(
        email: String,
        password: String,
    ): AuthError?

    suspend fun refreshToken(): AuthError?
}