package ru.ifedorov.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkCoursesResponse(
    val courses: List<NetworkCourseDto>
)
