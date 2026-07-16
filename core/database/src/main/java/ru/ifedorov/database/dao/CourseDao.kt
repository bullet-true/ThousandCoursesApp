package ru.ifedorov.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.ifedorov.database.entity.CourseEntity

@Dao
interface CourseDao {

    @Query("SELECT * FROM courses")
    fun observeCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses")
    suspend fun getCourses(): List<CourseEntity>

    @Query("SELECT * FROM courses WHERE is_favorite = 1")
    fun observeFavoriteCourses(): Flow<List<CourseEntity>>

    @Upsert
    suspend fun upsertCourses(courses: List<CourseEntity>)

    @Query("UPDATE courses SET is_favorite = :isFavorite WHERE course_id = :courseId")
    suspend fun updateFavorite(courseId: Int, isFavorite: Boolean)
}
