package com.example.todo.ui.onboarding.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.data.login.LoginCredentials
import com.example.todo.data.login.LoginFormState
import com.example.todo.data.session.Session
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth

    private val _uiState = MutableLiveData<LoginUiState>()
    val uiState: LiveData<LoginUiState> = _uiState

    private val _formState = LoginFormState()
    val formState: LoginFormState = _formState

    fun login() {
        val loginCredentials = LoginCredentials(
            _formState.email.value.trim(),
            _formState.password.value.trim()
        )
        auth.signInWithEmailAndPassword(loginCredentials.email, loginCredentials.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Session.currentUser = auth.currentUser
                    _uiState.value = LoginUiState.Success
                }
            }
            .addOnFailureListener {
                _uiState.value = LoginUiState.Error
            }
    }

    fun onEmailChanged(email: String) {
        _formState.email.value = email
    }

    fun onPasswordChanged(password: String) {
        _formState.password.value = password
    }
}

sealed class LoginUiState {
    object Success : LoginUiState()
    object Error : LoginUiState()
}