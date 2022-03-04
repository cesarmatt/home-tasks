package com.example.todo.ui.creation.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.example.todo.ui.components.ActionButtonComponent
import com.example.todo.ui.components.InputTaskComponent
import com.example.todo.ui.theme.*

class CreateTaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TasksTheme {
                    CreateTaskScreen()
                }
            }
        }
    }
}

@Composable
fun CreateTaskScreen() {
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

                InputTaskComponent(
                    label = "First let's give your task a title",
                    value = "",
                    onValueChange = { println("oie") })

                Spacer(modifier = Modifier.height(dimen4))

                InputTaskComponent(
                    label = "Select it's priority",
                    value = "",
                    onValueChange = { println("oie") }
                )

                Spacer(modifier = Modifier.height(dimen4))

                InputTaskComponent(
                    label = "What shift this task needs to be done?",
                    value = "",
                    onValueChange = { println("oie") }
                )

                Spacer(modifier = Modifier.height(dimen4))

                InputTaskComponent(
                    label = "We're almost there, what's the deadline?",
                    value = "",
                    onValueChange = { println("oie") }
                )
            }

            ActionButtonComponent(title = "Create your task!") {
                println("clicked")
            }
        }

    }
}

@Preview
@Composable
fun CreateTaskPreview() {
    CreateTaskScreen()
}