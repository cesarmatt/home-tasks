package com.example.todo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.todo.data.FirebaseTransactionResponse
import com.example.todo.data.HomeRepository
import com.example.todo.data.models.*
import com.example.todo.di.DaggerTodoComponent
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import javax.inject.Inject

class HomeViewModel() : ViewModel() {

    @Inject
    lateinit var repository: HomeRepository

    private val _formState = TaskFormState()

    private val _getTasks = liveData(Dispatchers.IO) {
        emit(HomeUiState.Loading)

        try {
            repository.getTasks().collect {
                if (it.isEmpty()) {
                    emit(HomeUiState.Empty)
                } else {
                    emit(HomeUiState.Success(it))
                }
            }
        } catch (e: Exception) {
            emit(HomeUiState.Error(IOException()))
        }
    }
    val tasks = _getTasks

    private val _createTransactionResponse = liveData(Dispatchers.IO) {
        try {
            repository.taskCreationStatus.collect {
                if (it) {
                    emit(FirebaseTransactionResponse.TransactionSuccess)
                } else {
                    emit(FirebaseTransactionResponse.TransactionIdle)
                }
            }
        } catch (e: Exception) {
            emit(FirebaseTransactionResponse.TransactionError(IOException()))
        }
    }
    val createTransactionResponse = _createTransactionResponse

    private val _completeTransactionResponse = liveData(Dispatchers.IO) {
        try {
            repository.taskDeletionStatus.collect {
                if (it) {
                    emit(FirebaseTransactionResponse.TransactionSuccess)
                } else {
                    emit(FirebaseTransactionResponse.TransactionIdle)
                }
            }
        } catch (e: Exception) {
            emit(FirebaseTransactionResponse.TransactionError(IOException()))
        }
    }
    val completeTransactionResponse = _completeTransactionResponse

    init {
        DaggerTodoComponent.create().inject(this)
    }

    fun save() {
        viewModelScope.launch {
            val task = makeTask()
            repository.postTask(task)
        }
    }

    fun complete(task: Task) {
        viewModelScope.launch {
            repository.completeTask(task)
        }
    }

    fun setTaskTitle(taskTitle: String) {
        _formState.title = taskTitle
    }

    fun setSelectedPriority(priorityPosition: Int) {
        val selectedOption = TaskPriority.values()[priorityPosition]
        _formState.priority = selectedOption
    }

    fun setSelectedShift(shiftPosition: Int) {
        val selectedOption = TaskShift.values()[shiftPosition]
        _formState.shift = selectedOption
    }

    private fun makeTask(): Task {
        return Task(
            id = generateId(),
            title = _formState.title,
            priority = _formState.priority,
            startDate = Timestamp.now(),
            deadline = makeTaskDeadline(),
            shift = _formState.shift
        )
    }

    private fun makeTaskDeadline(): Timestamp {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, _formState.priority.maximumTime)
        return Timestamp(calendar.time)
    }

    private fun generateId(): String {
        return UUID.randomUUID().toString()
    }

    fun refresh() {
        viewModelScope.launch {
            repository.getTasks()
        }
    }
}

sealed class HomeUiState {
    class Success(var tasks: List<Task>) : HomeUiState()
    class Error(var exception: Throwable) : HomeUiState()
    object Empty : HomeUiState()
    object Loading : HomeUiState()
}