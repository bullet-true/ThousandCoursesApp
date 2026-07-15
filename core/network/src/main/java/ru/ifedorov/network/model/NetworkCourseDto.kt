package ru.ifedorov.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCoursesResponse(
    val courses: List<NetworkCourseDto>
)

@Serializable
data class NetworkCourseDto(
    val id: Int,
    val title: String,
    @SerialName("text")
    val description: String,
    val price: String,
    @SerialName("rate")
    val rating: String,
    val startDate: String,
    @SerialName("hasLike")
    val isFavorite: Boolean,
    val publishDate: String
)
