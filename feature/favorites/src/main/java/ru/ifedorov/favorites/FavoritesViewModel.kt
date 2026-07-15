package ru.ifedorov.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ifedorov.domain.repository.CourseRepository
import ru.ifedorov.ui.component.CourseCardUiModel
import ru.ifedorov.ui.mapper.toCourseCardUiModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
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
                if (courses.isEmpty() && _uiState.value.isLoading) return@collect

                _uiState.update {
                    it.copy(
                        courses = courses
                            .filter { course -> course.isFavorite }
                            .map { course -> course.toCourseCardUiModel() },
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
        }
    }

    private fun loadCourses() {
        viewModelScope.launch {
            val result = runCatching {
                withContext(Dispatchers.IO) {
                    courseRepository.loadCourses()
                }
            }

            result.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message
                    )
                }
            }
        }
    }
}

data class FavoritesUiState(
    val courses: List<CourseCardUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
