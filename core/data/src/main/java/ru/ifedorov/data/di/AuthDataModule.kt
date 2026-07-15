package ru.ifedorov.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ifedorov.data.repository.FakeAuthRepository
import ru.ifedorov.domain.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = FakeAuthRepository()
}
