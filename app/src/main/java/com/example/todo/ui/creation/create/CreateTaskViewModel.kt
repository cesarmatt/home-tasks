package com.example.todo.ui.creation.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.create.CreateRepository
import com.example.todo.data.models.Task
import com.example.todo.data.create.TaskFormState
import com.example.todo.data.models.TaskPriority
import com.example.todo.data.models.TaskShift
import com.example.todo.ui.components.selector.TaskSelectorOption
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(private val repository: CreateRepository) : ViewModel() {

    private val _formState = TaskFormState()
    val formState: TaskFormState = _formState

    private val _uiState = MutableLiveData<CreateTaskUiState>()
    val uiState: LiveData<CreateTaskUiState> = _uiState

    fun save() {
        viewModelScope.launch {
            _uiState.value = CreateTaskUiState.Loading
            val task = makeTask()
            repository.postTask(task)
            repository.taskCreationResponse.collect { hasCreated ->
                if (hasCreated) {
                    _uiState.value = CreateTaskUiState.Success
                } else {
                    _uiState.value = CreateTaskUiState.Error
                }
            }
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

sealed class CreateTaskUiState {
    object Loading : CreateTaskUiState()
    object Success : CreateTaskUiState()
    object Error : CreateTaskUiState()
    object Idle : CreateTaskUiState()
}