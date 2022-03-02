package com.example.todo.data.models

enum class TaskPriority(val maximumTime: Int, val display: String) {
    LOW(7, "Baixa"),
    MEDIUM(4, "Média"),
    HIGH(1, "Alta")
}