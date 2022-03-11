package com.example.todo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.todo.ui.theme.dimen1

@Composable
fun TaskInputTextComponent(
    label: String,
    onValueChange: (String) -> Unit,
    value: String?,
    textState: MutableState<String>
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.caption
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            onValueChange = { onValueChange(it) }
        )
    }
}