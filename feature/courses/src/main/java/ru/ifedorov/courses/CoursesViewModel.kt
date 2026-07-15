package ru.ifedorov.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ifedorov.domain.repository.CourseRepository
import ru.ifedorov.ui.mapper.toCourseCardUiModel
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CoursesUiState>(CoursesUiState.Loading)
    val uiState: StateFlow<CoursesUiState> = _uiState.asStateFlow()

    init {
        observeCourses()
        loadCourses()
    }

    fun onFavoriteClick(courseId: Int) {
        viewModelScope.launch {
            courseRepository.toggleFavorite(courseId = courseId)
        }
    }

    private fun observeCourses() {
        viewModelScope.launch {
            courseRepository.observeCourses().collect { courses ->
                if (courses.isEmpty() && _uiState.value is CoursesUiState.Loading) return@collect

                _uiState.value = CoursesUiState.Content(
                    courses = courses.map { course -> course.toCourseCardUiModel() }
                )
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
                _uiState.value = CoursesUiState.Error(message = throwable.message)
            }
        }
    }
}
