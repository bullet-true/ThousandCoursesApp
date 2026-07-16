package ru.ifedorov.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ifedorov.database.AppDatabase
import ru.ifedorov.database.dao.CourseDao
import javax.inject.Singleton

private const val DATABASE_NAME = "thousand_courses.db"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context = context,
        klass = AppDatabase::class.java,
        name = DATABASE_NAME
    ).build()

    @Provides
    fun provideCourseDao(database: AppDatabase): CourseDao = database.courseDao()
}
