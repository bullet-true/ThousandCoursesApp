package ru.ifedorov.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey
    @ColumnInfo(name = "course_id")
    val courseId: Int,
    val title: String,
    val description: String,
    val price: String,
    val rating: String,
    @ColumnInfo(name = "start_date")
    val startDate: String,
    @ColumnInfo(name = "publish_date")
    val publishDate: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)
