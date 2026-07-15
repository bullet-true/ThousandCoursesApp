package ru.ifedorov.auth

sealed interface LoginUiEvent {
    data object InvalidCredentials : LoginUiEvent
}
