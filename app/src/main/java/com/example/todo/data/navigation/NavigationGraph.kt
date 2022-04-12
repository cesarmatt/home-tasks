package com.example.todo.data.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo.ui.creation.create.CreateTaskHoisting
import com.example.todo.ui.creation.create.CreateTaskViewModel
import com.example.todo.ui.home.feed.HomeViewModel
import com.example.todo.ui.home.feed.HomeScreenHoisting
import com.example.todo.ui.store.StoreHoisting

@Composable
fun NavigationGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = BottomNavItem.Home.screenRoute) {
        composable(BottomNavItem.Home.screenRoute) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreenHoisting(homeViewModel)
        }

        composable(BottomNavItem.Create.screenRoute) {
            val createTaskViewModel = hiltViewModel<CreateTaskViewModel>()
            CreateTaskHoisting(createTaskViewModel)
        }

        composable(BottomNavItem.Store.screenRoute) {
            StoreHoisting()
        }
    }
}

sealed class BottomNavItem(
    var title: String,
    val icon: ImageVector,
    var screenRoute: String
) {
    object Home : BottomNavItem("Home", Icons.Filled.Home, "home")
    object Create : BottomNavItem("Create", Icons.Filled.Add, "create")
    object Store : BottomNavItem("Store", Icons.Filled.ShoppingBag, "store")
}