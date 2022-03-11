package com.example.todo.data.create

import com.example.todo.data.models.Task

class CreateRepository(private val dataSource: CreateRemoteDataSource) {

    val taskCreationReponse = dataSource.taskCreationResponse

    suspend fun postTask(task: Task) {
        dataSource.postTask(task)
    }

}