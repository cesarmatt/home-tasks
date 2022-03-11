package com.example.todo.ui.components.inputs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun TaskInputTextComponent(
    label: String?,
    onValueChange: (String) -> Unit,
    textState: MutableState<String>
) {
    Column {
        Text(
            text = label.orEmpty(),
            style = MaterialTheme.typography.caption
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            onValueChange = { onValueChange(it) },
        )
    }
}