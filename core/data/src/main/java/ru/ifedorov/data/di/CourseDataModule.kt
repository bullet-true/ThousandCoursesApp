package ru.ifedorov.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ifedorov.data.repository.CourseRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CourseDataModule {

    @Binds
    @Singleton
    abstract fun bindCourseRepository(repository: CourseRepository): ru.ifedorov.domain.repository.CourseRepository
}
