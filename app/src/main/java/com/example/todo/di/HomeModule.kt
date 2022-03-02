package com.example.todo.di

import com.example.todo.data.HomeRepository
import dagger.Module
import dagger.Provides

@Module
class HomeModule {
    @Provides
    fun provideHomeRepository(): HomeRepository {
        return HomeRepository()
    }
}