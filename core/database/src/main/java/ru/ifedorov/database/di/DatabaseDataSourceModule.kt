package ru.ifedorov.database.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ifedorov.database.datastore.CoursesLocalDataSource
import ru.ifedorov.database.datastore.RoomCoursesLocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindCoursesLocalDataSource(
        dataSource: RoomCoursesLocalDataSource
    ): CoursesLocalDataSource
}
