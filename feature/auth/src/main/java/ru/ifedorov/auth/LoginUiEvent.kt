package ru.ifedorov.auth

sealed interface LoginUiEvent {

    data object LoginSuccess : LoginUiEvent
    data object InvalidCredentials : LoginUiEvent
}
