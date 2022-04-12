package com.example.todo.di

import com.example.todo.data.create.CreateRemoteDataSource
import com.example.todo.data.create.CreateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object CreateTaskModule {

    @Provides
    fun provideCreateTaskRemoteDataSource(): CreateRemoteDataSource {
        return CreateRemoteDataSource()
    }

    @Provides
    fun provideCreateTaskRepository(): CreateRepository {
        return CreateRepository(CreateRemoteDataSource())
    }
}