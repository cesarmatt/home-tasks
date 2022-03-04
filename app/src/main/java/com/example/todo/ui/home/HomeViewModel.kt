package com.example.todo.ui

import androidx.lifecycle.*
import com.example.todo.data.HomeRepository
import com.example.todo.data.models.*
import com.example.todo.di.DaggerTodoComponent
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    @Inject
    lateinit var repository: HomeRepository

    val db = Firebase.firestore

    private val _formState = TaskFormState()

    private val _uiState = MutableLiveData<HomeUiState>()
    val uiState: LiveData<HomeUiState> = _uiState

    private val _createTransactionUiState = MutableLiveData<CreateTransactionUiState>()
    val createTransactionUiState: LiveData<CreateTransactionUiState> = _createTransactionUiState

    private val _completeTransactionUiState = MutableLiveData<CompleteTransactionUiState>()
    val completeTransactionUiState: LiveData<CompleteTransactionUiState> =
        _completeTransactionUiState

    init {
        DaggerTodoComponent.create().inject(this)
        getTaskList()
    }

    private fun getTaskList() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            db.collection("tasks")
                .orderBy("startDate", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { snapshot ->
                    val list = snapshot?.toObjects(Task::class.java) as List<Task>
                    val definitiveList = list.filter { it.endDate == null && !it.isCompleted }
                    _uiState.value = HomeUiState.Success(definitiveList)
                }
                .addOnFailureListener {
                    _uiState.value = HomeUiState.Error(IOException())
                }
        }
    }

    fun save() {
        viewModelScope.launch {
            val task = makeTask()
            db.collection("tasks")
                .add(task)
                .addOnSuccessListener {
                    refresh()
                }
                .addOnFailureListener {
                    _createTransactionUiState.value =
                        CreateTransactionUiState.Error(IOException("Could not create your task"))
                }
        }
    }

    fun complete(task: Task) {
        viewModelScope.launch {
            db.collection("tasks")
                .document(task.id)
                .update(
                    mapOf(
                        "isCompleted" to true,
                        "endDate" to Date()
                    )
                )
                .addOnSuccessListener {
                    refresh()
                }
                .addOnFailureListener {
                    _completeTransactionUiState.value =
                        CompleteTransactionUiState.Error(IOException("Could not complete your task"))
                }
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
        getTaskList()
    }
}

sealed class HomeUiState {
    class Success(var tasks: List<Task>) : HomeUiState()
    class Error(var exception: Throwable) : HomeUiState()
    object Empty : HomeUiState()
    object Loading : HomeUiState()
}

sealed class CreateTransactionUiState {
    object Success : CreateTransactionUiState()
    class Error(var exception: Throwable) : CreateTransactionUiState()
    object Loading : CreateTransactionUiState()
}

sealed class CompleteTransactionUiState {
    object Success : CompleteTransactionUiState()
    class Error(var exception: Throwable) : CompleteTransactionUiState()
    object Loading : CompleteTransactionUiState()
}