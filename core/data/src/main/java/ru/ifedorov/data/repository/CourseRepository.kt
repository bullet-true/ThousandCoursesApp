package ru.ifedorov.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import ru.ifedorov.data.mapper.toDomain
import ru.ifedorov.database.datastore.CoursesLocalDataSource
import ru.ifedorov.domain.model.Course
import ru.ifedorov.domain.repository.CourseRepository
import ru.ifedorov.network.datasource.CoursesNetworkDataSource
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val DELAY_MS = 2_000L

class CourseRepository @Inject constructor(
    private val coursesNetworkDataSource: CoursesNetworkDataSource,
    private val coursesLocalDataSource: CoursesLocalDataSource
) : CourseRepository {

    override fun observeCourses(): Flow<List<Course>> =
        coursesLocalDataSource.observeCourses()

    override suspend fun loadCourses() {
        delay(DELAY_MS.milliseconds)

        val cachedFavoriteState = coursesLocalDataSource.getCourses().associate { course ->
            course.id to course.isFavorite
        }
        val courses = coursesNetworkDataSource.getCourses().map { courseDto ->
            val course = courseDto.toDomain()
            course.copy(isFavorite = cachedFavoriteState[course.id] ?: course.isFavorite)
        }

        coursesLocalDataSource.upsertCourses(courses)
    }

    override suspend fun toggleFavorite(courseId: Int) {
        val course =
            coursesLocalDataSource.getCourses().firstOrNull { course -> course.id == courseId }
                ?: return

        coursesLocalDataSource.updateFavorite(
            courseId = courseId,
            isFavorite = !course.isFavorite
        )
    }
}
