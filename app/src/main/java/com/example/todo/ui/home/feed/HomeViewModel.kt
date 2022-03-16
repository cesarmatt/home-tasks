package com.example.todo.ui.home.feed

import androidx.lifecycle.*
import com.example.todo.data.models.*
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val db = Firebase.firestore

    private val _uiState = MutableLiveData<HomeUiState>()
    val uiState: LiveData<HomeUiState> = _uiState

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