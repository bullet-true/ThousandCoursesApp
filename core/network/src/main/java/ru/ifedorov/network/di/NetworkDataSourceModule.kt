package ru.ifedorov.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ifedorov.network.datasource.CoursesNetworkDataSource
import ru.ifedorov.network.datasource.RetrofitCoursesNetworkDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindCoursesNetworkDataSource(
        dataSource: RetrofitCoursesNetworkDataSource
    ): CoursesNetworkDataSource
}
