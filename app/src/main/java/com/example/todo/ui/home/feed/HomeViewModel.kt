package com.example.todo.ui.home

import androidx.lifecycle.*
import com.example.todo.data.home.HomeRepository
import com.example.todo.data.models.*
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    val db = Firebase.firestore

    private val _uiState = MutableLiveData<HomeUiState>()
    val uiState: LiveData<HomeUiState> = _uiState

    private val _createTransactionUiState = MutableLiveData<CreateTransactionUiState>()
    val createTransactionUiState: LiveData<CreateTransactionUiState> = _createTransactionUiState

    private val _completeTransactionUiState = MutableLiveData<CompleteTransactionUiState>()
    val completeTransactionUiState: LiveData<CompleteTransactionUiState> =
        _completeTransactionUiState

    init {
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