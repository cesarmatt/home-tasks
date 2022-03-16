package com.example.todo.ui.creation.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import com.example.todo.ui.theme.noElevation

@Composable
fun CreateTaskTopAppBarComponent() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = noElevation,
        title = {
            Text(
                "Criando uma nova tarefa",
                style = MaterialTheme.typography.h6,
            )
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Navigation clicked")
            }
        }
    )
}