package com.normalnywork.plants.ui.screens.auth

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.normalnywork.plants.R
import com.normalnywork.plants.ui.kit.components.AppTextField
import com.normalnywork.plants.ui.navigation.screen.SignInComponent

@Composable
fun SignInScreen(component: SignInComponent) {
    val isLoading by component.isLoading.collectAsState()

    AuthScreenCore(
        title = stringResource(R.string.signin_title),
        question = stringResource(R.string.signin_question),
        switchToOtherScreen = component::switchToSignUp,
        textFields = {
            val email by component.email.collectAsState()
            val password by component.password.collectAsState()

            AppTextField(
                label = stringResource(R.string.auth_email),
                icon = painterResource(R.drawable.ic_email_small),
                value = email,
                onValueChange = component::onEmailChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            AppTextField(
                label = stringResource(R.string.auth_password),
                icon = painterResource(R.drawable.ic_key_small),
                value = password,
                onValueChange = component::onPasswordChange,
                isPassword = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    autoCorrectEnabled = false,
                ),
            )
        },
        actionText = stringResource(R.string.signin_action),
        onActionClick = component::signIn,
        isLoading = isLoading,
    )
}