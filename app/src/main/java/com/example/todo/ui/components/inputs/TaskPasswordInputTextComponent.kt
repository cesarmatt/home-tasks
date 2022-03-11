package com.example.todo.ui.components.inputs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TaskPasswordInputTextComponent(
    label: String?,
    onValueChange: (String) -> Unit,
    textState: MutableState<String>
) {
    var isTextVisible by remember { mutableStateOf(false) }
    Column {
        Text(
            text = label.orEmpty(),
            style = MaterialTheme.typography.caption
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            visualTransformation = if (isTextVisible) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = { onValueChange(it) },
            trailingIcon = {
                val icon = if (isTextVisible) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }

                val description = if (isTextVisible) "Hide password" else "Show password"

                IconButton(onClick = { isTextVisible = !isTextVisible }) {
                    Icon(imageVector = icon, description)
                }
            }
        )
    }
}