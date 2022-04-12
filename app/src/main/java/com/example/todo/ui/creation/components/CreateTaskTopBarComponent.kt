package com.example.todo.ui.creation.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.ui.theme.noElevation

@Composable
fun CreateTaskTopAppBarComponent(onCreateClicked: () -> Unit) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = noElevation,
        title = {
            Text(
                "Criando uma nova tarefa",
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Start
            )
        },
        actions = {
            IconButton(onClick = { onCreateClicked() }) {
                Icon(Icons.Filled.Check, contentDescription = "Create")
            }
        }
    )
}

@Composable
@Preview
fun CreateTaskTopAppBarPreview() {
    CreateTaskTopAppBarComponent { println("Create!") }
}