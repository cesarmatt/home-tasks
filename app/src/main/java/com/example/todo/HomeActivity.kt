package com.example.todo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.data.navigation.NavigationGraph
import com.example.todo.ui.components.navigation.BottomNavigationComponent
import com.example.todo.ui.theme.TasksTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasksTheme {
                MainScreenView()
            }
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationComponent(navController)
        }
    ) {
        NavigationGraph(navHostController = navController)
    }
}