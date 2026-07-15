package ru.ifedorov.auth

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

private const val VK_URL = "https://vk.ru/"
private const val OK_URL = "https://ok.ru/"

@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val invalidCredentialsMessage = stringResource(id = R.string.auth_invalid_credentials)

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                LoginUiEvent.LoginSuccess -> {
                    onLoginSuccess()
                }

                LoginUiEvent.InvalidCredentials -> {
                    Toast.makeText(context, invalidCredentialsMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LoginScreen(
        modifier = modifier,
        isLoading = uiState.isLoading,
        onLoginClick = { email, password ->
            viewModel.onLoginClick(email, password)
        },
        onRegistrationClick = {},
        onForgotPasswordClick = {},
        onVkLoginClick = { uriHandler.openUri(VK_URL) },
        onOkLoginClick = { uriHandler.openUri(OK_URL) }
    )
}
