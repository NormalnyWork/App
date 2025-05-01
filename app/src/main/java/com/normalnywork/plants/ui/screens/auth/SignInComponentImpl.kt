package com.normalnywork.plants.ui.screens.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.normalnywork.plants.domain.models.AuthError
import com.normalnywork.plants.ui.navigation.screen.SignInComponent
import kotlinx.coroutines.flow.MutableStateFlow

class SignInComponentImpl(
    componentContext: ComponentContext,
    private val navigateToSignUp: () -> Unit,
) : SignInComponent, ComponentContext by componentContext {

    private val stateHolder = instanceKeeper.getOrCreate { StateHolder() }

    override val email = MutableStateFlow(stateHolder.email)
    override val password = MutableStateFlow(stateHolder.password)

    override val isLoading = MutableStateFlow(false)
    override val error = MutableStateFlow(stateHolder.error)

    override fun onEmailChange(newEmail: String) {
        stateHolder.email = newEmail
        email.value = newEmail
    }

    override fun onPasswordChange(newPassword: String) {
        stateHolder.password = newPassword
        password.value = newPassword
    }

    override fun switchToSignUp() = navigateToSignUp()

    override fun signIn() {
        TODO("Not yet implemented")
    }

    class StateHolder : InstanceKeeper.Instance {

        var email: String = ""
        var password: String = ""

        var error: AuthError? = null
    }
}