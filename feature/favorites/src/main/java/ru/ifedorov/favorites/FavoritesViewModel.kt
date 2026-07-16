package ru.ifedorov.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ifedorov.domain.repository.CourseRepository
import ru.ifedorov.ui.mapper.toCourseCardUiModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        observeFavoriteCourses()
        loadCourses()
    }

    fun onFavoriteClick(courseId: Int) {
        viewModelScope.launch {
            courseRepository.toggleFavorite(courseId = courseId)
        }
    }

    private fun observeFavoriteCourses() {
        viewModelScope.launch {
            courseRepository.observeFavoriteCourses().collect { courses ->

                _uiState.value = FavoritesUiState.Content(
                    courses = courses.map { course -> course.toCourseCardUiModel() }
                )
            }
        }
    }

    private fun loadCourses() {
        viewModelScope.launch {
            val result = runCatching {
                courseRepository.loadCourses()
            }

            result.onFailure { throwable ->
                _uiState.value = FavoritesUiState.Error(message = throwable.message)
            }
        }
    }
}
