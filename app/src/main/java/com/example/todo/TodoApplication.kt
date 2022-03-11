package com.example.todo

import android.app.Application
import com.example.todo.di.createModule
import com.example.todo.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@TodoApplication)
            modules(
                homeModule,
                createModule
            )
        }
    }
}