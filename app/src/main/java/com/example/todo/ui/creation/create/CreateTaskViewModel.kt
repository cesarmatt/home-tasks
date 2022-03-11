package com.example.todo.ui.creation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.create.CreateRepository
import com.example.todo.data.models.Task
import com.example.todo.data.create.TaskFormState
import com.example.todo.data.models.TaskPriority
import com.example.todo.data.models.TaskShift
import com.example.todo.ui.components.selector.TaskSelectorOption
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import java.util.*

class CreateTaskViewModel(private val repository: CreateRepository) : ViewModel() {

    private val _formState = TaskFormState()
    val formState: TaskFormState = _formState

    fun save() {
        viewModelScope.launch {
            val task = makeTask()
            println(task)
            repository.postTask(task)
        }
    }

    private fun makeTask(): Task {
        return Task(
            id = generateId(),
            title = _formState.title.value,
            priority = _formState.priority.value,
            startDate = Timestamp.now(),
            deadline = makeTaskDeadline(),
            shift = _formState.shift.value
        )
    }

    private fun makeTaskDeadline(): Timestamp {
        val calendar = Calendar.getInstance()
        calendar.add(
            Calendar.DAY_OF_MONTH,
            _formState.priority.value.maximumTime
        )
        return Timestamp(calendar.time)
    }

    private fun generateId(): String {
        return UUID.randomUUID().toString()
    }

    fun onTitleChanged(title: String) {
        _formState.title.value = title
    }

    fun onPrioritySelected(priority: TaskPriority) {
        _formState.priority.value = priority
    }

    fun onShiftSelected(shift: TaskShift) {
        _formState.shift.value = shift
    }

    fun getPrioritySelectorOptions(): List<TaskSelectorOption<TaskPriority>> {
        return TaskPriority.values().map { priority ->
            TaskSelectorOption(priority.display, priority)
        }
    }

    fun getShiftSelectorOptions(): List<TaskSelectorOption<TaskShift>> {
        return TaskShift.values().map { shift ->
            TaskSelectorOption(shift.display, shift)
        }
    }
}