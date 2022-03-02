package com.example.todo.data.models

import com.google.firebase.Timestamp

data class Task(
    var id: String = "",
    var title: String = "",
    var priority: TaskPriority? = TaskPriority.LOW,
    var startDate: Timestamp = Timestamp.now(),
    var endDate: Timestamp? = null,
    var deadline: Timestamp? = null,
    var shift: TaskShift = TaskShift.MORNING,
    var isCompleted: Boolean = false
)
