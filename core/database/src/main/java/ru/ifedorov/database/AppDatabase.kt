package ru.ifedorov.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ifedorov.database.dao.CourseDao
import ru.ifedorov.database.entity.CourseEntity

@Database(
    entities = [
        CourseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao
}
