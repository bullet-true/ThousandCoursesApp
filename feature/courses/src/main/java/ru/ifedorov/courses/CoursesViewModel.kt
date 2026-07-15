package ru.ifedorov.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
import kotlin.time.Duration.Companion.milliseconds

private const val DELAY_MS = 2_000

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CoursesUiState())
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
                if (courses.isEmpty() && _uiState.value.isLoading) return@collect

                _uiState.update {
                    it.copy(
                        courses = courses.map { course -> course.toCourseCardUiModel() },
                        isLoading = false,
                        errorMessage = null
                    )
                }
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

data class CoursesUiState(
    val courses: List<CourseCardUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
