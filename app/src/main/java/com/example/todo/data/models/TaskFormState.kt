package com.example.todo.data.models

import java.util.*

class TaskFormState(
    var title: String = "",
    var priority: TaskPriority = TaskPriority.LOW,
    val startDate: Date = Date(),
    val endDate: Date? = null,
    val deadline: Date? = null,
    var shift: TaskShift = TaskShift.MORNING
)