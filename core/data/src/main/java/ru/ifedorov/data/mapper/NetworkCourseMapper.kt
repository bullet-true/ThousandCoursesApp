package ru.ifedorov.data.mapper

import ru.ifedorov.domain.model.Course
import ru.ifedorov.network.model.NetworkCourseDto

fun NetworkCourseDto.toDomain(): Course {
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
