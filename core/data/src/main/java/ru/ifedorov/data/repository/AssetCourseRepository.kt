package ru.ifedorov.data.repository

import android.content.res.AssetManager
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

    override suspend fun getCourses(): List<Course> =
        assetManager.open(COURSES_ASSET_NAME).bufferedReader().use { reader ->
            val coursesResponse: CoursesResponseDto = json.decodeFromString<CoursesResponseDto>(
                reader.readText()
            )

            coursesResponse.courses.map { courseDto -> courseDto.toDomain() }
        }

    private companion object {
        const val COURSES_ASSET_NAME = "courses.json"
    }
}
