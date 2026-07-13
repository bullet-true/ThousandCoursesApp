package ru.ifedorov.domain.repository

import ru.ifedorov.domain.model.Course

interface CourseRepository {
    suspend fun getCourses(): List<Course>
}
