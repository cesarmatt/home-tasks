package com.example.todo.data.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class LoginFormState(
    var email: MutableState<String> = mutableStateOf(""),
    var password: MutableState<String> = mutableStateOf("")
)