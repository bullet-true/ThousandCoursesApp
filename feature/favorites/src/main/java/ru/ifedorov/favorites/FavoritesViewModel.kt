package ru.ifedorov.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ifedorov.domain.repository.CourseRepository
import ru.ifedorov.ui.mapper.toCourseCardUiModel
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val DELAY_MS = 2_000

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
            courseRepository.observeCourses().collect { courses ->
                if (courses.isEmpty() && _uiState.value is FavoritesUiState.Loading) return@collect

                _uiState.value = FavoritesUiState.Content(
                    courses = courses
                        .filter { course -> course.isFavorite }
                        .map { course -> course.toCourseCardUiModel() }
                )
            }
        }
    }

    private fun loadCourses() {
        viewModelScope.launch {
            delay(DELAY_MS.milliseconds)

            val result = runCatching {
                withContext(Dispatchers.IO) {
                    courseRepository.loadCourses()
                }
            }

            result.onFailure { throwable ->
                _uiState.value = FavoritesUiState.Error(message = throwable.message)
            }
        }
    }
}
