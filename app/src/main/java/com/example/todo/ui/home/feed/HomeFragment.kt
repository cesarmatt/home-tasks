package com.example.todo.ui.home.feed

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.example.todo.ui.components.TaskLoaderComponent
import com.example.todo.ui.creation.CreateTaskActivity
import com.example.todo.ui.home.HomeUiState
import com.example.todo.ui.home.HomeViewModel
import com.example.todo.ui.home.components.topbar.HomeTopBarComponent
import com.example.todo.ui.home.components.topbar.TaskItemComponent
import com.example.todo.ui.theme.TasksTheme
import com.example.todo.ui.theme.dimen2
import com.example.todo.ui.theme.dimen4
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.refresh()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                TasksTheme {
                    HomeScreenHoisting(viewModel) {
                        onFloatingActionButtonClicked()
                    }
                }
            }
        }
    }

    private fun onFloatingActionButtonClicked() {
        val intent = Intent(requireContext(), CreateTaskActivity::class.java)
        startForResult.launch(intent)
    }

//    private fun setupBindings() {
//        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
//            when (uiState) {
//                is HomeUiState.Success -> setupAdapter(uiState.tasks)
//                is HomeUiState.Empty -> setupEmptyState()
//                is HomeUiState.Error -> setupErrorState()
//                is HomeUiState.Loading -> setupLoadingState()
//            }
//        }
//
//        viewModel.createTransactionUiState.observe(viewLifecycleOwner) { transactionUiState ->
//            when (transactionUiState) {
//                is CreateTransactionUiState.Success -> onCreationSuccess()
//                is CreateTransactionUiState.Error -> onCreationFailure()
//            }
//        }
//
//        viewModel.completeTransactionUiState.observe(viewLifecycleOwner) { transactionUiState ->
//            when (transactionUiState) {
//                is CompleteTransactionUiState.Success -> onCompleteSuccess()
//                is CompleteTransactionUiState.Error -> onCompleteFailure()
//            }
//        }
//    }
}

@Composable
fun HomeScreenHoisting(
    homeViewModel: HomeViewModel,
    onFloatingActionButtonClicked: () -> Unit
) {
    val uiState: HomeUiState by homeViewModel.uiState.observeAsState(HomeUiState.Loading)
    HomeScreen(uiState) {
        onFloatingActionButtonClicked()
    }
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onFloatingActionButtonClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        topBar = {
            HomeTopBarComponent()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFloatingActionButtonClicked() },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    Icons.Rounded.Add,
                    "create task",
                    tint = MaterialTheme.colors.onSecondary
                )
            }
        }
    ) {
        when (uiState) {
            is HomeUiState.Success -> {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = dimen4, vertical = dimen2),
                ) {
                    items(uiState.tasks) {
                        TaskItemComponent(task = it)
                    }
                }
            }
            is HomeUiState.Loading -> {
                TaskLoaderComponent()
            }
        }
    }
}