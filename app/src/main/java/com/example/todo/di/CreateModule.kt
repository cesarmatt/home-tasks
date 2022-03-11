package com.example.todo.di

import com.example.todo.data.create.CreateRemoteDataSource
import com.example.todo.data.create.CreateRepository
import com.example.todo.ui.creation.create.CreateTaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val createModule = module {
    factory { CreateRemoteDataSource() }
    factory { CreateRepository(get()) }
    viewModel { CreateTaskViewModel(get()) }
}