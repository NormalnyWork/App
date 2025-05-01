package com.normalnywork.plants.ui.screens.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.normalnywork.plants.domain.models.AuthError
import com.normalnywork.plants.ui.navigation.screen.SignUpComponent
import kotlinx.coroutines.flow.MutableStateFlow

class SignUpComponentImpl(
    componentContext: ComponentContext,
    private val navigateToSignIn: () -> Unit,
) : SignUpComponent, ComponentContext by componentContext {

    private val stateHolder = instanceKeeper.getOrCreate { StateHolder() }

    override val username = MutableStateFlow(stateHolder.username)
    override val email = MutableStateFlow(stateHolder.email)
    override val password = MutableStateFlow(stateHolder.password)

    override val isLoading = MutableStateFlow(false)
    override val error = MutableStateFlow(stateHolder.error)

    override fun onUsernameChange(newUsername: String) {
        stateHolder.username = newUsername
        username.value = newUsername
    }

    override fun onEmailChange(newEmail: String) {
        stateHolder.email = newEmail
        email.value = newEmail
    }

    override fun onPasswordChange(newPassword: String) {
        stateHolder.password = newPassword
        password.value = newPassword
    }

    override fun switchToSignIn() = navigateToSignIn()

    override fun signUp() {
        TODO("Not yet implemented")
    }

    class StateHolder : InstanceKeeper.Instance {

        var username: String = ""
        var email: String = ""
        var password: String = ""

        var error: AuthError? = null
    }
}