package com.example.todo.ui.onboarding.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.todo.HomeActivity
import com.example.todo.data.login.LoginFormState
import com.example.todo.ui.components.TaskActionButtonComponent
import com.example.todo.ui.components.inputs.TaskInputTextComponent
import com.example.todo.ui.components.inputs.TaskPasswordInputTextComponent
import com.example.todo.ui.theme.TasksTheme
import com.example.todo.ui.theme.dimen4
import com.example.todo.ui.theme.dimen6
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TasksTheme {
                    LoginHoisting(viewModel) {
                        onLoginClicked()
                    }
                }
            }
        }
    }

    private fun onLoginClicked() {
        viewModel.login()
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                is LoginUiState.Success -> {
                    goToHome()
                }
            }
        }
    }

    private fun goToHome() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        activity?.finish()
    }
}

@Composable
fun LoginHoisting(
    loginViewModel: LoginViewModel,
    onLoginClicked: () -> Unit
) {
    val formState = remember { loginViewModel.formState }
    LoginScreen(
        formState = formState,
        onEmailChanged = { loginViewModel.onEmailChanged(it) },
        onPasswordChanged = { loginViewModel.onPasswordChanged(it) },
        onLoginClicked = { onLoginClicked() }
    )
}

@Composable
fun LoginScreen(
    formState: LoginFormState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(dimen4),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Welcome",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = dimen4)
                )

                Spacer(modifier = Modifier.height(dimen6))

                Column(modifier = Modifier.padding(dimen4)) {
                    TaskInputTextComponent(
                        label = "Email",
                        onValueChange = { onEmailChanged(it) },
                        textState = formState.email
                    )

                    Spacer(modifier = Modifier.height(dimen4))

                    TaskPasswordInputTextComponent(
                        label = "Password",
                        onValueChange = { onPasswordChanged(it) },
                        textState = formState.password
                    )
                }

                Column(modifier = Modifier.padding(dimen4)) {
                    TaskActionButtonComponent(title = "Login") {
                        onLoginClicked()
                    }
                }
            }
        }
    }
}