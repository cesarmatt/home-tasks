package com.example.todo.ui.home.components.topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.data.models.Task
import com.example.todo.ui.theme.defaultElevation
import com.example.todo.ui.theme.dimen2
import com.example.todo.ui.theme.dimen4

@Composable
fun TaskItemComponent(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = dimen2)
            .clickable {  },
        elevation = defaultElevation,
    ) {
        Column(modifier = Modifier.padding(dimen4)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    val task = Task()
    task.title = "Task title"
    TaskItemComponent(task)
}