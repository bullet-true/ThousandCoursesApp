package ru.ifedorov.database.mapper

import ru.ifedorov.database.entity.CourseEntity
import ru.ifedorov.domain.model.Course

fun CourseEntity.toDomain(): Course {
    return Course(
        id = courseId,
        title = title,
        description = description,
        price = price,
        rating = rating,
        startDate = startDate,
        isFavorite = isFavorite,
        publishDate = publishDate
    )
}

fun Course.toEntity(): CourseEntity {
    return CourseEntity(
        courseId = id,
        title = title,
        description = description,
        price = price,
        rating = rating,
        startDate = startDate,
        publishDate = publishDate,
        isFavorite = isFavorite
    )
}
