package com.example.todo.data.home

import com.example.todo.data.models.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class HomeRepository {

    val db = Firebase.firestore
    val taskDeletionStatus = MutableStateFlow(false)

    suspend fun getTasks(): Flow<List<Task>> = callbackFlow {
        val eventDocument = FirebaseFirestore
            .getInstance()
            .collection("tasks")
            .orderBy("startDate", Query.Direction.DESCENDING)

        val subscription = eventDocument.addSnapshotListener { snapshot, _ ->
            val list = snapshot?.toObjects(Task::class.java) as List<Task>
            val definitiveList = list.filter { it.endDate == null }
            trySend(definitiveList)
        }

        awaitClose { subscription.remove() }
    }

    fun completeTask(task: Task) {
        db.collection("tasks")
            .document(task.id)
            .update(mapOf(
                "endDate" to Date(),
                "isCompleted" to true
            ))
            .addOnSuccessListener {
                taskDeletionStatus.tryEmit(true)
            }
            .addOnFailureListener {
                taskDeletionStatus.tryEmit(false)
            }
    }
}