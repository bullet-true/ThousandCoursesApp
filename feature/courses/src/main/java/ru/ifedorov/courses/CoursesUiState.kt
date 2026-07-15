package ru.ifedorov.courses

import ru.ifedorov.ui.component.CourseCardUiModel

sealed interface CoursesUiState {

    data object Loading : CoursesUiState

    data class Error(val message: String?) : CoursesUiState

    data class Content(val courses: List<CourseCardUiModel>) : CoursesUiState
}
