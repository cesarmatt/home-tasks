package com.example.todo.data.create

import com.example.todo.data.models.Task
import kotlinx.coroutines.flow.drop

class CreateRepository(private val dataSource: CreateRemoteDataSource) {

    val taskCreationResponse = dataSource.taskCreationResponse.drop(1)

    suspend fun postTask(task: Task) {
        dataSource.postTask(task)
    }

}