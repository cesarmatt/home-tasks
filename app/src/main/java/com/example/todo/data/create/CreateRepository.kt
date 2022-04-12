package com.example.todo.data.create

import com.example.todo.data.models.Task
import kotlinx.coroutines.flow.drop
import javax.inject.Inject

class CreateRepository @Inject constructor(private val dataSource: CreateRemoteDataSource) {

    val taskCreationResponse = dataSource.taskCreationResponse.drop(1)

    suspend fun postTask(task: Task) {
        dataSource.postTask(task)
    }

}