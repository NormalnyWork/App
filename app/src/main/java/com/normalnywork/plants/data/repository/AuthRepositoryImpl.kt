package com.normalnywork.plants.data.repository

import android.util.Log
import android.util.Patterns
import com.normalnywork.plants.data.api.AuthService
import com.normalnywork.plants.data.api.auth.TokenStore
import com.normalnywork.plants.data.api.models.Error
import com.normalnywork.plants.data.api.models.SignInResponse
import com.normalnywork.plants.data.api.models.SignUpRequest
import com.normalnywork.plants.domain.entity.AuthError
import com.normalnywork.plants.domain.repository.AuthRepository
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException

class AuthRepositoryImpl : AuthRepository, KoinComponent {

    private val authService: AuthService by inject()
    private val tokenStore: TokenStore by inject()
    private val json: Json by inject()

    override suspend fun signUp(
        username: String,
        email: String,
        password: String,
    ): AuthError? {
        if (username.isEmpty()) return AuthError.UsernameEmpty
        if (email.isEmpty()) return AuthError.EmailEmpty
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return AuthError.InvalidEmail
        if (password.isEmpty()) return AuthError.PasswordEmpty
        if (password.length < 8) return AuthError.WeakPassword

        runCatchingAuth {
            authService.signUp(
                SignUpRequest(
                    name = username,
                    email = email,
                    password = password,
                )
            )
        }?.let { error -> return error }

        return signIn(email, password)
    }

    override suspend fun signIn(
        email: String,
        password: String,
    ): AuthError? {
        if (email.isEmpty()) return AuthError.EmailEmpty
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return AuthError.InvalidEmail
        if (password.isEmpty()) return AuthError.PasswordEmpty

        return runCatchingAuth {
            authService.signIn(
                email = email,
                password = password,
            ).also { it.saveTokens() }
        }
    }

    override suspend fun refreshToken(): AuthError? {
        return runCatchingAuth {
            authService.refreshToken(tokenStore.getRefreshToken()!!)
                .also { it.saveTokens() }
        }
    }

    private suspend fun runCatchingAuth(
        block: suspend () -> Unit,
    ): AuthError? {
        runCatching {
            block()
        }.onFailure {
            return when (it) {
                is HttpException -> {
                    val message = runCatching {
                        it.response()?.errorBody()?.string()
                            ?.let { json.decodeFromString<Error>(it).message }
                    }.onFailure {
                        Log.e("AuthRepository", "Failed to parse error response:", it)
                    }.getOrNull()

                    when (message) {
                        Error.EMAIL_ALREADY_USED -> AuthError.EmailTaken
                        Error.NAME_ALREADY_USED -> AuthError.UsernameTaken
                        Error.INVALID_CREDENTIALS -> AuthError.InvalidCredentials
                        else -> {
                            Log.e("AuthRepository", "Unknown error message: $message")
                            AuthError.Unknown
                        }
                    }
                }
                else -> {
                    Log.e("AuthRepository", "Unknown exception:", it)
                    AuthError.Unknown
                }
            }
        }
        return null
    }

    private fun SignInResponse.saveTokens() = with(tokenStore) {
        saveAccessToken(accessToken)
        saveRefreshToken(refreshToken)
    }
}