package com.normalnywork.plants.data.api.auth

import com.normalnywork.plants.data.api.ApiHeaders
import com.normalnywork.plants.data.api.ApiRoutes
import com.normalnywork.plants.domain.repository.AppRepository
import com.normalnywork.plants.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthInterceptor : Interceptor, KoinComponent {

    private val authRepository: AuthRepository by inject()
    private val appRepository: AppRepository by inject()
    private val tokenStore: TokenStore by inject()

    override fun intercept(chain: Interceptor.Chain) = chain.run {
        val requiresBearerToken = request().header(ApiHeaders.AUTHORIZATION).isNullOrEmpty().not()
        val token = tokenStore.getAccessToken()

        val requestBuilder = request().newBuilder()
            .apply { if (requiresBearerToken && token != null) signRequest(token) }
        val response = proceed(requestBuilder.build())

        if (response.code == UNAUTHORIZED) {
            if (response.request.url.toUrl().path == ApiRoutes.REFRESH) {
                tokenStore.logout()
                appRepository.restart()
            } else {
                val newResponse = runBlocking {
                    response.close()

                    if (authRepository.refreshToken() == null) {
                        tokenStore.getAccessToken()?.let {
                            requestBuilder.signRequest(it)
                        }
                        val repeatResponse = proceed(requestBuilder.build())

                        return@runBlocking repeatResponse
                    }
                    return@runBlocking response
                }
                return@run newResponse
            }
        }
        return@run response
    }

    private fun Request.Builder.signRequest(token: String) {
        removeHeader(ApiHeaders.BEARER_TOKEN)
        removeHeader(ApiHeaders.AUTHORIZATION)
        addHeader(ApiHeaders.AUTHORIZATION, "$TOKEN_TYPE $token")
    }

    companion object {

        private const val UNAUTHORIZED = 401
        private const val TOKEN_TYPE = "Bearer"
    }
}