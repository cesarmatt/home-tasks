package com.example.todo.ui.home.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.todo.ui.components.TaskLoaderComponent
import com.example.todo.ui.home.components.topbar.HomeTopBarComponent
import com.example.todo.ui.home.components.topbar.TaskItemComponent
import com.example.todo.ui.theme.dimen2
import com.example.todo.ui.theme.dimen4

@Composable
fun HomeScreenHoisting(homeViewModel: HomeViewModel) {
    val uiState: HomeUiState by homeViewModel.uiState.observeAsState(HomeUiState.Loading)
    HomeScreen(uiState = uiState)
}

@Composable
fun HomeScreen(uiState: HomeUiState) {
    Scaffold(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        topBar = {
            HomeTopBarComponent()
        }
    ) {
        when (uiState) {
            is HomeUiState.Success -> {
                Column {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = dimen4, vertical = dimen2),
                    ) {
                        items(uiState.tasks) {
                            TaskItemComponent(task = it)
                        }
                    }
                }
            }
            is HomeUiState.Loading -> {
                TaskLoaderComponent()
            }
        }
    }
}