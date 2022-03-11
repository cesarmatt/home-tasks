package com.example.todo.ui.home.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.example.todo.ui.creation.CreateTaskActivity

class CreateTask : ActivityResultContract<Int, Boolean?>() {
    override fun createIntent(context: Context, input: Int): Intent {
        return Intent(context, CreateTaskActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        if (resultCode != Activity.RESULT_OK) {
            return false
        }
        return true
    }
}