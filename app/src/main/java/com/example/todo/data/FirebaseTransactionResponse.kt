package com.example.todo.data

sealed class FirebaseTransactionResponse {
    object TransactionSuccess : FirebaseTransactionResponse()
    class TransactionError(var exception: Throwable) : FirebaseTransactionResponse()
    object TransactionLoading : FirebaseTransactionResponse()
    object TransactionIdle: FirebaseTransactionResponse()
}