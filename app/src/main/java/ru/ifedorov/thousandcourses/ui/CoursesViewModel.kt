package ru.ifedorov.thousandcourses.ui

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
import ru.ifedorov.thousandcourses.mapper.toCourseCardUiModel
import ru.ifedorov.ui.component.CourseCardUiModel
import javax.inject.Inject

data class CoursesUiState(
    val courses: List<CourseCardUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CoursesUiState())
    val uiState: StateFlow<CoursesUiState> = _uiState.asStateFlow()

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            val result = runCatching {
                withContext(Dispatchers.IO) {
                    courseRepository.getCourses().map { course -> course.toCourseCardUiModel() }
                }
            }

            result.onSuccess { courses ->
                _uiState.update {
                    it.copy(
                        courses = courses,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }.onFailure { throwable ->
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
