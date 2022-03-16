package com.example.todo.ui.creation.create

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.data.create.TaskFormState
import com.example.todo.data.models.TaskPriority
import com.example.todo.data.models.TaskShift
import com.example.todo.ui.components.TaskActionButtonComponent
import com.example.todo.ui.components.inputs.TaskInputTextComponent
import com.example.todo.ui.components.selector.TaskSelectorComponent
import com.example.todo.ui.components.selector.TaskSelectorOption
import com.example.todo.ui.creation.components.CreateTaskTopAppBarComponent
import com.example.todo.ui.theme.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@Composable
fun CreateTaskHoisting() {
//    createTaskViewModel: CreateTaskViewModel = viewModel(CreateTaskViewModel::class.java)
    Text("Create")
//    val formState = remember { createTaskViewModel.formState }
//    CreateTaskScreen(
//        formState = formState,
//        onTitleChanged = { createTaskViewModel.onTitleChanged(it) },
//        onPrioritySelected = { createTaskViewModel.onPrioritySelected(it) },
//        onShiftSelected = { createTaskViewModel.onShiftSelected(it) },
//        prioritySelectorOptions = createTaskViewModel.getPrioritySelectorOptions(),
//        shiftSelectorOptions = createTaskViewModel.getShiftSelectorOptions(),
//    )
}

@Composable
fun CreateTaskScreen(
    formState: TaskFormState,
    onTitleChanged: (String) -> Unit,
    onPrioritySelected: (TaskPriority) -> Unit,
    onShiftSelected: (TaskShift) -> Unit,
    prioritySelectorOptions: List<TaskSelectorOption<TaskPriority>>,
    shiftSelectorOptions: List<TaskSelectorOption<TaskShift>>
) {
    Scaffold(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        topBar = { CreateTaskTopAppBarComponent() }
    ) {
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

            TaskActionButtonComponent(title = "Create your task!") {
                println("clicked")
            }
        }

    }
}