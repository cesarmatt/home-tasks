package com.example.todo.ui.creation.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.todo.data.create.TaskFormState
import com.example.todo.data.models.TaskPriority
import com.example.todo.data.models.TaskShift
import com.example.todo.ui.components.TaskActionButtonComponent
import com.example.todo.ui.components.TaskLoaderComponent
import com.example.todo.ui.components.inputs.TaskInputTextComponent
import com.example.todo.ui.components.selector.TaskSelectorComponent
import com.example.todo.ui.components.selector.TaskSelectorOption
import com.example.todo.ui.creation.components.CreateTaskTopAppBarComponent
import com.example.todo.ui.home.feed.HomeUiState
import com.example.todo.ui.theme.*

@Composable
fun CreateTaskHoisting(createTaskViewModel: CreateTaskViewModel) {
    val formState = remember { createTaskViewModel.formState }
    val navController = rememberNavController()
    val uiState: CreateTaskUiState by createTaskViewModel.uiState.observeAsState(CreateTaskUiState.Idle)
    CreateTaskScreen(
        uiState = uiState,
        formState = formState,
        onTitleChanged = { createTaskViewModel.onTitleChanged(it) },
        onPrioritySelected = { createTaskViewModel.onPrioritySelected(it) },
        onShiftSelected = { createTaskViewModel.onShiftSelected(it) },
        prioritySelectorOptions = createTaskViewModel.getPrioritySelectorOptions(),
        shiftSelectorOptions = createTaskViewModel.getShiftSelectorOptions(),
        onCreateClicked = {
            createTaskViewModel.save()
            navController.popBackStack()
        }
    )
}

@Composable
fun CreateTaskScreen(
    uiState: CreateTaskUiState,
    formState: TaskFormState,
    onTitleChanged: (String) -> Unit,
    onPrioritySelected: (TaskPriority) -> Unit,
    onShiftSelected: (TaskShift) -> Unit,
    prioritySelectorOptions: List<TaskSelectorOption<TaskPriority>>,
    shiftSelectorOptions: List<TaskSelectorOption<TaskShift>>,
    onCreateClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        topBar = { CreateTaskTopAppBarComponent { onCreateClicked() } }
    ) {
        when (uiState) {
            is CreateTaskUiState.Idle -> {
                Column(modifier = Modifier.padding(dimen4)) {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Spacer(modifier = Modifier.height(dimen6))

                        TaskInputTextComponent(
                            label = "First let's give your task a title",
                            onValueChange = { onTitleChanged(it) },
                            textState = formState.title
                        )

                        Spacer(modifier = Modifier.height(dimen4))

                        TaskSelectorComponent(
                            label = "Select it's priority",
                            items = prioritySelectorOptions,
                            onItemSelected = { onPrioritySelected(it) }
                        )

                        Spacer(modifier = Modifier.height(dimen4))

                        TaskSelectorComponent(
                            label = "What shift this task needs to be done?",
                            items = shiftSelectorOptions,
                            onItemSelected = { onShiftSelected(it) }
                        )

                    }
                }
            }

            is CreateTaskUiState.Success -> {
                Column {

                }
            }

            is CreateTaskUiState.Error -> {
                Text("Deu ruim")
            }

            is CreateTaskUiState.Loading -> {
                TaskLoaderComponent()
            }
        }
    }
}