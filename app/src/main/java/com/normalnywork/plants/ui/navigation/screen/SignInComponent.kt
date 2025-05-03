package com.normalnywork.plants.ui.navigation.screen

import com.normalnywork.plants.domain.entity.AuthError
import kotlinx.coroutines.flow.StateFlow

interface SignInComponent {

    val email: StateFlow<String>
    val password: StateFlow<String>

    val isLoading: StateFlow<Boolean>
    val error: StateFlow<AuthError?>

    fun onEmailChange(newEmail: String)
    fun onPasswordChange(newPassword: String)

    fun switchToSignUp()

    fun signIn()
}