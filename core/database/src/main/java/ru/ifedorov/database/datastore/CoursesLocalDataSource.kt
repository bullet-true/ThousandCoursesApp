package ru.ifedorov.database.datastore

import kotlinx.coroutines.flow.Flow
import ru.ifedorov.domain.model.Course

interface CoursesLocalDataSource {

    fun observeCourses(): Flow<List<Course>>

    fun observeFavoriteCourses(): Flow<List<Course>>

    suspend fun upsertCourses(courses: List<Course>)

    suspend fun updateFavorite(courseId: Int, isFavorite: Boolean)
}
