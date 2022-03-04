package com.example.todo.ui.creation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.todo.R

class CreateTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.create_task_navhost_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.setGraph(R.navigation.create_nav_graph)
    }
}