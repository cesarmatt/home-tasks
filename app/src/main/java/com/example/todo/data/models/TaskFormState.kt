package com.example.todo.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.util.*

class TaskFormState(
    var title: MutableState<String> = mutableStateOf(""),
    var priority: MutableState<TaskPriority> = mutableStateOf(TaskPriority.LOW),
    val startDate: MutableState<Date> = mutableStateOf(Date()),
    val endDate: MutableState<Date> = mutableStateOf(Date()),
    val deadline: MutableState<Date> = mutableStateOf(Date()),
    var shift: MutableState<TaskShift> = mutableStateOf(TaskShift.MORNING)
)