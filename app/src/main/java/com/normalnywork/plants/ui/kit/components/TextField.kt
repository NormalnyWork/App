package com.normalnywork.plants.ui.kit.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.normalnywork.plants.R
import com.normalnywork.plants.ui.kit.style.LocalAppColors
import com.normalnywork.plants.ui.kit.style.LocalAppShapes
import com.normalnywork.plants.ui.kit.style.LocalAppTypography

@Composable
fun AppTextField(
    label: String,
    icon: Painter,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextWithIcon(
            text = label,
            icon = icon,
            tint = LocalAppColors.current.textSecondary
        )
        var passwordShown by remember { mutableStateOf(!isPassword) }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            isError = error != null,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LocalAppColors.current.background,
                unfocusedContainerColor = LocalAppColors.current.background,
                errorContainerColor = LocalAppColors.current.background,
                unfocusedIndicatorColor = LocalAppColors.current.stroke,
                focusedIndicatorColor = LocalAppColors.current.primary,
                errorIndicatorColor = LocalAppColors.current.error,
                cursorColor = LocalAppColors.current.primary,
                errorCursorColor = LocalAppColors.current.error,
                unfocusedTextColor = LocalAppColors.current.textPrimary,
                focusedTextColor = LocalAppColors.current.textPrimary,
                errorTextColor = LocalAppColors.current.textPrimary,
            ),
            shape = LocalAppShapes.current.medium,
            textStyle = LocalAppTypography.current.body1,
            trailingIcon = if (isPassword) {{
                IconButton(
                    onClick = { passwordShown = !passwordShown }
                ) {
                    Icon(
                        painter = painterResource(
                            if (passwordShown) R.drawable.ic_hide_password
                            else R.drawable.ic_show_password
                        ),
                        contentDescription = stringResource(
                            if (passwordShown) R.string.auth_hide_password
                            else R.string.auth_show_password
                        ),
                        tint = LocalAppColors.current.primary,
                    )
                }
            }} else null,
            visualTransformation = if (passwordShown) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = keyboardOptions,
        )
        AnimatedContent(
            targetState = error,
            transitionSpec = {
                (fadeIn() togetherWith fadeOut()).using(SizeTransform(clip = false))
            },
        ) { errorContent ->
            if (errorContent != null) TextWithIcon(
                text = errorContent,
                icon = painterResource(R.drawable.ic_error_small),
                tint = LocalAppColors.current.error,
            )
        }
    }
}

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val style = LocalAppTypography.current.body4

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = style.copy(color = LocalAppColors.current.textPrimary),
        cursorBrush = SolidColor(LocalAppColors.current.primary),
        decorationBox = { inputView ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        color = LocalAppColors.current.backgroundSecondary,
                        shape = CircleShape,
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    tint = LocalAppColors.current.textSecondary,
                )
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) Text(
                        text = stringResource(R.string.handbook_search_placeholder),
                        style = style,
                        color = LocalAppColors.current.textSecondary,
                    )
                    inputView()
                }
                AnimatedVisibility(
                    visible = value.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(
                        onClick = { onValueChange("") },
                        modifier = Modifier.size(24.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_clear),
                            contentDescription = stringResource(R.string.handbook_search_clear),
                            tint = LocalAppColors.current.textSecondary,
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun TextWithIcon(
    text: String,
    icon: Painter,
    tint: Color,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(18.dp),
        )
        Text(
            text = text,
            style = LocalAppTypography.current.body3,
            color = tint,
        )
    }
}