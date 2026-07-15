package ru.ifedorov.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.ifedorov.data.mapper.toDomain
import ru.ifedorov.domain.model.Course
import ru.ifedorov.domain.repository.CourseRepository
import ru.ifedorov.network.datasource.CoursesNetworkDataSource
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val DELAY_MS = 2_000L

class CourseRepository @Inject constructor(
    private val coursesNetworkDataSource: CoursesNetworkDataSource
) : CourseRepository {

    private val courses = MutableStateFlow<List<Course>>(emptyList())
    private val loadMutex = Mutex()

    override fun observeCourses(): Flow<List<Course>> = courses.asStateFlow()

    override suspend fun loadCourses() {
        delay(DELAY_MS.milliseconds)

        if (courses.value.isNotEmpty()) return

        loadMutex.withLock {
            if (courses.value.isNotEmpty()) return@withLock

            courses.value = coursesNetworkDataSource.getCourses().map { courseDto ->
                courseDto.toDomain()
            }
        }
    }

    override suspend fun toggleFavorite(courseId: Int) {
        courses.update { currentCourses ->
            currentCourses.map { course ->
                if (course.id == courseId) {
                    course.copy(isFavorite = !course.isFavorite)
                } else {
                    course
                }
            }
        }
    }
}
