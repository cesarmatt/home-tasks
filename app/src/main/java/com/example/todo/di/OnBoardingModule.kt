package com.example.todo.di

import com.example.todo.ui.onboarding.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onBoardingModule = module {
    viewModel { LoginViewModel() }
}