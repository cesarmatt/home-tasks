package com.example.todo.di

import com.example.todo.data.HomeRepository
import com.example.todo.ui.HomeViewModel
import dagger.Component

@Component(modules = [HomeModule::class])
interface TodoComponent {
    fun inject(repository: HomeRepository)
    fun inject(viewModel: HomeViewModel)
}