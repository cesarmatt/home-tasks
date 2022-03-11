package com.example.todo.ui.creation.create

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.todo.data.create.TaskFormState
import com.example.todo.data.models.TaskPriority
import com.example.todo.data.models.TaskShift
import com.example.todo.ui.components.TaskActionButtonComponent
import com.example.todo.ui.components.inputs.TaskInputTextComponent
import com.example.todo.ui.components.selector.TaskSelectorComponent
import com.example.todo.ui.components.selector.TaskSelectorOption
import com.example.todo.ui.theme.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateTaskFragment : Fragment() {

    private val viewModel: CreateTaskViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TasksTheme {
                    CreateTaskHoisting(
                        createTaskViewModel = viewModel,
                        onCreateClicked = { onCreateClicked() }
                    )
                }
            }
        }
    }

    private fun onCreateClicked() {
        viewModel.save()
        activity?.setResult(Activity.RESULT_OK)
        activity?.finish()
    }
}

@Composable
fun CreateTaskHoisting(
    createTaskViewModel: CreateTaskViewModel,
    onCreateClicked: () -> Unit
) {
    val formState = remember { createTaskViewModel.formState }
    CreateTaskScreen(
        formState = formState,
        onTitleChanged = { createTaskViewModel.onTitleChanged(it) },
        onPrioritySelected = { createTaskViewModel.onPrioritySelected(it) },
        onShiftSelected = { createTaskViewModel.onShiftSelected(it) },
        prioritySelectorOptions = createTaskViewModel.getPrioritySelectorOptions(),
        shiftSelectorOptions = createTaskViewModel.getShiftSelectorOptions(),
        onCreateClicked = { onCreateClicked() }
    )
}

@Composable
fun CreateTaskScreen(
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
    ) {
        Column(modifier = Modifier.padding(dimen4)) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = "Let's create a new task!",
                    style = MaterialTheme.typography.h6
                )

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
                onCreateClicked()
            }
        }

    }
}