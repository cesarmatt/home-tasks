package com.example.todo.di

import com.example.todo.data.home.HomeRepository
import com.example.todo.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { HomeRepository() }
    viewModel { HomeViewModel(get()) }
}