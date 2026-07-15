package ru.ifedorov.favorites

import ru.ifedorov.ui.component.CourseCardUiModel

sealed interface FavoritesUiState {

    data object Loading : FavoritesUiState

    data class Error(val message: String?) : FavoritesUiState

    data class Content(val courses: List<CourseCardUiModel>) : FavoritesUiState
}
