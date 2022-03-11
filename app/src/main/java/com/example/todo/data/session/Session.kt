package com.example.todo.data.session

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo

object Session {
    var currentUser: FirebaseUser? = null
    var userInfo: UserInfo? = null
}