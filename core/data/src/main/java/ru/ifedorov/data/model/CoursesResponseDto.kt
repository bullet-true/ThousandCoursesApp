package ru.ifedorov.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CoursesResponseDto(
    val courses: List<CourseDto>
)
