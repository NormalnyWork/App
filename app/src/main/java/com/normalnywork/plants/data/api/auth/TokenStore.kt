package com.normalnywork.plants.data.api.auth

interface TokenStore {

    fun getAccessToken(): String?

    fun saveAccessToken(token: String)

    fun getRefreshToken(): String?

    fun saveRefreshToken(token: String)

    fun logout()
}