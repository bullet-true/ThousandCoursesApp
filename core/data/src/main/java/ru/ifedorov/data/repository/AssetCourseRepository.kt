package ru.ifedorov.data.repository

import android.content.res.AssetManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import ru.ifedorov.data.model.CoursesResponseDto
import ru.ifedorov.data.model.toDomain
import ru.ifedorov.domain.model.Course
import ru.ifedorov.domain.repository.CourseRepository

class AssetCourseRepository(
    private val assetManager: AssetManager,
    private val json: Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
) : CourseRepository {

    private val courses = MutableStateFlow<List<Course>>(emptyList())
    private val loadMutex = Mutex()

    override fun observeCourses(): Flow<List<Course>> = courses.asStateFlow()

    override suspend fun loadCourses() {
        if (courses.value.isNotEmpty()) return

        loadMutex.withLock {
            if (courses.value.isNotEmpty()) return@withLock
            courses.value = readCoursesFromAssets()
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

    private fun readCoursesFromAssets() = assetManager.open(COURSES_ASSET_NAME)
        .bufferedReader().use { reader ->
            val coursesResponse = json.decodeFromString<CoursesResponseDto>(
                reader.readText()
            )

            coursesResponse.courses.map { courseDto -> courseDto.toDomain() }
        }

    private companion object {
        const val COURSES_ASSET_NAME = "courses.json"
    }
}
