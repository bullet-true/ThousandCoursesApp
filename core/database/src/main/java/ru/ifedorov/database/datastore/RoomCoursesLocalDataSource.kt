package ru.ifedorov.database.datastore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ifedorov.database.dao.CourseDao
import ru.ifedorov.database.mapper.toDomain
import ru.ifedorov.database.mapper.toEntity
import ru.ifedorov.domain.model.Course
import javax.inject.Inject

class RoomCoursesLocalDataSource @Inject constructor(
    private val courseDao: CourseDao
) : CoursesLocalDataSource {

    override fun observeCourses(): Flow<List<Course>> =
        courseDao.observeCourses().map { courses ->
            courses.map { course -> course.toDomain() }
        }

    override fun observeFavoriteCourses(): Flow<List<Course>> =
        courseDao.observeFavoriteCourses().map { courses ->
            courses.map { course -> course.toDomain() }
        }

    override suspend fun upsertCourses(courses: List<Course>) {
        courseDao.upsertCourses(courses.map { course -> course.toEntity() })
    }

    override suspend fun updateFavorite(courseId: Int, isFavorite: Boolean) {
        courseDao.updateFavorite(courseId = courseId, isFavorite = isFavorite)
    }
}
