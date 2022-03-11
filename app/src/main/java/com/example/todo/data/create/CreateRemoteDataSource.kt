package com.example.todo.data.create

import com.example.todo.data.models.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

class CreateRemoteDataSource {

    val db = Firebase.firestore

    private val _taskCreationResponse = MutableStateFlow(false)
    val taskCreationResponse = _taskCreationResponse

    suspend fun postTask(task: Task) {
        db.collection("tasks")
            .add(task)
            .addOnSuccessListener {
                taskCreationResponse.tryEmit(true)

            }
            .addOnFailureListener {
                taskCreationResponse.tryEmit(false)
            }
    }
}