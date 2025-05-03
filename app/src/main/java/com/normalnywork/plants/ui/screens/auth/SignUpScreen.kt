package com.normalnywork.plants.ui.screens.auth

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.normalnywork.plants.R
import com.normalnywork.plants.domain.entity.AuthError.EmailEmpty
import com.normalnywork.plants.domain.entity.AuthError.EmailTaken
import com.normalnywork.plants.domain.entity.AuthError.InvalidEmail
import com.normalnywork.plants.domain.entity.AuthError.PasswordEmpty
import com.normalnywork.plants.domain.entity.AuthError.Unknown
import com.normalnywork.plants.domain.entity.AuthError.UsernameEmpty
import com.normalnywork.plants.domain.entity.AuthError.UsernameTaken
import com.normalnywork.plants.domain.entity.AuthError.WeakPassword
import com.normalnywork.plants.ui.kit.components.AppTextField
import com.normalnywork.plants.ui.navigation.screen.SignUpComponent

@Composable
fun SignUpScreen(component: SignUpComponent) {
    val isLoading by component.isLoading.collectAsState()

    AuthScreenCore(
        title = stringResource(R.string.signup_title),
        question = stringResource(R.string.signup_question),
        switchToOtherScreen = component::switchToSignIn,
        textFields = {
            val username by component.username.collectAsState()
            val email by component.email.collectAsState()
            val password by component.password.collectAsState()
            val error by component.error.collectAsState()

            AppTextField(
                label = stringResource(R.string.auth_username),
                icon = painterResource(R.drawable.ic_user_small),
                value = username,
                onValueChange = component::onUsernameChange,
                error = when (error) {
                    UsernameEmpty -> stringResource(R.string.auth_error_username_empty)
                    UsernameTaken -> stringResource(R.string.auth_error_username_taken)
                    Unknown -> stringResource(R.string.auth_error_unknown)
                    else -> null
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            AppTextField(
                label = stringResource(R.string.auth_email),
                icon = painterResource(R.drawable.ic_email_small),
                value = email,
                onValueChange = component::onEmailChange,
                error = when (error) {
                    EmailEmpty,
                    InvalidEmail -> stringResource(R.string.auth_error_invalid_email)
                    EmailTaken -> stringResource(R.string.auth_error_email_taken)
                    Unknown -> stringResource(R.string.auth_error_unknown)
                    else -> null
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
            )
            AppTextField(
                label = stringResource(R.string.auth_password),
                icon = painterResource(R.drawable.ic_key_small),
                value = password,
                onValueChange = component::onPasswordChange,
                error = when (error) {
                    PasswordEmpty -> stringResource(R.string.auth_error_empty_password)
                    WeakPassword -> stringResource(R.string.auth_error_weak_password)
                    Unknown -> stringResource(R.string.auth_error_unknown)
                    else -> null
                },
                isPassword = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Done,
                ),
            )
        },
        actionText = stringResource(R.string.signup_action),
        onActionClick = component::signUp,
        isLoading = isLoading,
    )
}