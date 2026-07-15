package ru.ifedorov.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ifedorov.auth.validator.LoginInputValidator
import ru.ifedorov.ui.theme.OkButtonGradientEndColor
import ru.ifedorov.ui.theme.OkButtonGradientStartColor
import ru.ifedorov.ui.theme.ThousandCoursesTheme
import ru.ifedorov.ui.theme.VkButtonColor

private const val PLACEHOLDER_ALPHA = 0.5f
private val AuthTextFieldPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
private val AuthComponentHeight = 40.dp
private val AuthCornerRadius = 24.dp
private val ButtonCornerRadius = 30.dp

private val EmailInputTransformation = InputTransformation {
    if (LoginInputValidator.containsCyrillic(input = asCharSequence())) {
        revertAllChanges()
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onLoginClick: (email: String, password: String) -> Unit,
    onRegistrationClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onVkLoginClick: () -> Unit,
    onOkLoginClick: () -> Unit,
) {
    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()

    val isLoginEnabled = LoginInputValidator.isLoginInputValid(
        email = emailState.text,
        password = passwordState.text
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.auth_login_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(28.dp))

        AuthFieldLabel(text = stringResource(R.string.auth_email_label))

        Spacer(modifier = Modifier.height(8.dp))

        AuthTextField(
            state = emailState,
            placeholder = stringResource(R.string.auth_email_placeholder),
            inputTransformation = EmailInputTransformation
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthFieldLabel(text = stringResource(R.string.auth_password_label))

        Spacer(modifier = Modifier.height(8.dp))

        AuthTextField(
            state = passwordState,
            placeholder = stringResource(R.string.auth_password_placeholder),
            isPassword = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        AuthButton(
            text = stringResource(R.string.auth_login_button),
            enabled = !isLoading && isLoginEnabled,
            onClick = {
                onLoginClick(
                    emailState.text.toString(),
                    passwordState.text.toString()
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LoginSupportLinks(
            onRegistrationClick = onRegistrationClick,
            onForgotPasswordClick = onForgotPasswordClick
        )

        Spacer(modifier = Modifier.height(32.dp))

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline,
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(32.dp))

        SocialButtons(
            onVkLoginClick = onVkLoginClick,
            onOkLoginClick = onOkLoginClick
        )
    }
}

@Composable
private fun AuthFieldLabel(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AuthTextField(
    state: TextFieldState,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    inputTransformation: InputTransformation? = null
) {
    if (isPassword) {
        OutlinedSecureTextField(
            state = state,
            modifier = modifier.authTextFieldModifier(),
            shape = RoundedCornerShape(AuthCornerRadius),
            placeholder = { AuthTextFieldPlaceholder(text = placeholder) },
            contentPadding = AuthTextFieldPadding,
            colors = AuthTextFieldColors()
        )
    } else {
        OutlinedTextField(
            state = state,
            modifier = modifier.authTextFieldModifier(),
            lineLimits = TextFieldLineLimits.SingleLine,
            shape = RoundedCornerShape(AuthCornerRadius),
            placeholder = { AuthTextFieldPlaceholder(text = placeholder) },
            inputTransformation = inputTransformation,
            contentPadding = AuthTextFieldPadding,
            colors = AuthTextFieldColors()
        )
    }
}

@Composable
private fun AuthTextFieldPlaceholder(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = PLACEHOLDER_ALPHA),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun AuthButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(AuthComponentHeight),
        shape = RoundedCornerShape(ButtonCornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun LoginSupportLinks(
    onRegistrationClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(R.string.auth_no_account),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = stringResource(R.string.auth_registration),
                modifier = Modifier.clickable(
                    onClick = {
                        onRegistrationClick
                    }
                ),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.auth_forgot_password),
            modifier = Modifier.clickable(
                onClick = {
                    onForgotPasswordClick
                }
            ),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun SocialButtons(
    onVkLoginClick: () -> Unit,
    onOkLoginClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SocialButton(
            iconResId = R.drawable.ic_vk,
            contentDescription = stringResource(R.string.auth_login_with_vk),
            background = Brush.horizontalGradient(
                colors = listOf(VkButtonColor, VkButtonColor)
            ),
            onClick = onVkLoginClick,
            modifier = Modifier.weight(1f),
        )
        SocialButton(
            iconResId = R.drawable.ic_ok,
            contentDescription = stringResource(R.string.auth_login_with_ok),
            background = Brush.horizontalGradient(
                colors = listOf(OkButtonGradientStartColor, OkButtonGradientEndColor)
            ),
            onClick = onOkLoginClick,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun SocialButton(
    iconResId: Int,
    contentDescription: String,
    background: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(AuthComponentHeight)
            .clickable(
                onClick = onClick,
                role = Role.Button
            )
            .background(
                brush = background,
                shape = RoundedCornerShape(ButtonCornerRadius)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = contentDescription,
            tint = Color.Unspecified
        )
    }
}

private fun Modifier.authTextFieldModifier(): Modifier = fillMaxWidth().height(AuthComponentHeight)

@Composable
private fun AuthTextFieldColors(): TextFieldColors =
    OutlinedTextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        focusedTextColor = MaterialTheme.colorScheme.onBackground,
        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = PLACEHOLDER_ALPHA),
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = PLACEHOLDER_ALPHA),
        cursorColor = MaterialTheme.colorScheme.primary
    )

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    ThousandCoursesTheme {
        LoginScreen(
            onLoginClick = { _, _ -> },
            onRegistrationClick = {},
            onForgotPasswordClick = {},
            onVkLoginClick = {},
            onOkLoginClick = {}
        )
    }
}

@Preview()
@Composable
private fun AuthButtonPreview() {
    ThousandCoursesTheme {
        AuthButton(
            text = "Вход",
            enabled = true,
            onClick = {}
        )
    }
}

@Preview()
@Composable
private fun LoginSupportLinksPreview() {
    ThousandCoursesTheme {
        LoginSupportLinks(
            onRegistrationClick = {},
            onForgotPasswordClick = {}
        )
    }
}

@Preview()
@Composable
private fun SocialButtonsPreview() {
    ThousandCoursesTheme {
        SocialButtons(
            onVkLoginClick = {},
            onOkLoginClick = {}
        )
    }
}
