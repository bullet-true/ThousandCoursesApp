package ru.ifedorov.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.ifedorov.domain.model.Course

@Serializable
data class CourseDto(
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

fun CourseDto.toDomain(): Course {
    return Course(
        id = id,
        title = title,
        description = description,
        price = price,
        rating = rating,
        startDate = startDate,
        isFavorite = isFavorite,
        publishDate = publishDate
    )
}
