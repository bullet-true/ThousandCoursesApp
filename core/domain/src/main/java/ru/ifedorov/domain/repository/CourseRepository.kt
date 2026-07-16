package ru.ifedorov.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.ifedorov.domain.model.Course

interface CourseRepository {
    fun observeCourses(): Flow<List<Course>>

    fun observeFavoriteCourses(): Flow<List<Course>>

    suspend fun loadCourses()

    suspend fun toggleFavorite(courseId: Int)
}
