package com.normalnywork.plants.ui.screens.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.normalnywork.plants.domain.entity.AuthError
import com.normalnywork.plants.domain.entity.AuthError.EmailEmpty
import com.normalnywork.plants.domain.entity.AuthError.InvalidCredentials
import com.normalnywork.plants.domain.entity.AuthError.InvalidEmail
import com.normalnywork.plants.domain.entity.AuthError.PasswordEmpty
import com.normalnywork.plants.domain.entity.AuthError.Unknown
import com.normalnywork.plants.domain.repository.AuthRepository
import com.normalnywork.plants.ui.navigation.screen.SignInComponent
import com.normalnywork.plants.utils.componentScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignInComponentImpl(
    componentContext: ComponentContext,
    private val navigateToSignUp: () -> Unit,
    private val navigateToHome: () -> Unit,
) : SignInComponent, ComponentContext by componentContext, KoinComponent {

    private val authRepository: AuthRepository by inject()

    private val stateHolder = instanceKeeper.getOrCreate { StateHolder() }

    override val email = MutableStateFlow(stateHolder.email)
    override val password = MutableStateFlow(stateHolder.password)

    override val isLoading = MutableStateFlow(false)
    override val error = MutableStateFlow(stateHolder.error)

    override fun onEmailChange(newEmail: String) {
        stateHolder.email = newEmail
        email.value = newEmail

        if (error.value in listOf(EmailEmpty, InvalidEmail, InvalidCredentials, Unknown))
            resetError()
    }

    override fun onPasswordChange(newPassword: String) {
        stateHolder.password = newPassword
        password.value = newPassword

        if (error.value in listOf(PasswordEmpty, InvalidCredentials, Unknown))
            resetError()
    }

    override fun switchToSignUp() = navigateToSignUp()

    override fun signIn() {
        componentScope.launch {
            withLoading {
                authRepository.signIn(
                    email = email.value,
                    password = password.value,
                ).let { authError ->
                    if (authError == null) {
                        navigateToHome()
                    } else {
                        error.value = authError
                        stateHolder.error = authError
                    }
                }
            }
        }
    }

    private fun resetError() {
        error.value = null
        stateHolder.error = null
    }

    private suspend fun withLoading(block: suspend () -> Unit) {
        isLoading.value = true
        block()
        isLoading.value = false
    }

    class StateHolder : InstanceKeeper.Instance {

        var email: String = ""
        var password: String = ""

        var error: AuthError? = null
    }
}