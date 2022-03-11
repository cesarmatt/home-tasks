package com.example.todo.ui.components.selector

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.data.models.TaskPriority
import com.example.todo.ui.theme.dimen1
import com.example.todo.ui.theme.dimen4

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> TaskSelectorComponent(
    label: String,
    items: List<TaskSelectorOption<T>>,
    onItemSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(items.first().display) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.caption
        )
        Spacer(modifier = Modifier.height(dimen1))
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = MaterialTheme.colors.background
                )
            )
            ExposedDropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach {
                    DropdownMenuItem(
                        onClick = {
                            onItemSelected(it.optionValue)
                            selectedOptionText = it.display
                            expanded = false
                        }
                    ) {
                        Text(text = it.display)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskSelectorPreview() {
    TaskSelectorComponent(
        label = "Label",
        items = TaskPriority.values().map { TaskSelectorOption(it.display, it.maximumTime) },
        onItemSelected = { println("oi") })
}
