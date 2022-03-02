package com.example.todo.ui

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.FirebaseTransactionResponse
import com.example.todo.data.models.Task
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.ext.hideKeyboard
import com.example.todo.ui.creation.CreateTaskBottomSheet
import com.google.android.material.textfield.TextInputEditText

class HomeFragment : Fragment() {

    lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private val adapter = HomeAdapter(
        onTaskChecked = { onTaskCheckboxClicked(it) }
    )

    private var homeContent: LinearLayout? = null
    private var taskList: RecyclerView? = null
    private var inputTask: TextInputEditText? = null
    private var saveTask: Button? = null
    private var progress: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupBindings()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        homeContent = binding.homeContent
        taskList = binding.taskList
        inputTask = binding.inputTask
        saveTask = binding.saveTask
        progress = binding.root.findViewById(R.id.progress)

        return view
    }

    private fun setupViews() {
        saveTask?.setOnClickListener {
            val inputText = inputTask?.text
            if (inputText.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Task inválida ou vazia", Toast.LENGTH_LONG).show()
            } else {
                it.hideKeyboard()
                onSaveClicked(inputText.trim().toString())
            }
        }

        taskList?.let {
            it.adapter = adapter
        }
    }

    private fun setupBindings() {
        viewModel.tasks.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is HomeUiState.Success -> setupAdapter(uiState.tasks)
                is HomeUiState.Empty -> setupEmptyState()
                is HomeUiState.Error -> setupErrorState()
                is HomeUiState.Loading -> setupLoadingState()
            }
        }

        viewModel.createTransactionResponse.observe(viewLifecycleOwner) { creationResponse ->
            when (creationResponse) {
                is FirebaseTransactionResponse.TransactionSuccess -> onCreationSuccess()
                is FirebaseTransactionResponse.TransactionError -> onCreationFailure()
            }
        }

        viewModel.completeTransactionResponse.observe(viewLifecycleOwner) { deletionResponse ->
            when (deletionResponse) {
                is FirebaseTransactionResponse.TransactionSuccess -> onCompleteSuccess()
                is FirebaseTransactionResponse.TransactionError -> onCompleteFailure()
            }
        }
    }

    private fun setupAdapter(tasks: List<Task>) {
        progress?.isVisible = false
        homeContent?.isVisible = true
        adapter.setTasks(tasks)
    }

    private fun setupLoadingState() {
        homeContent?.isVisible = false
        progress?.isVisible = true
    }

    private fun setupEmptyState() {

    }

    private fun setupErrorState() {

    }

    private fun onCreationSuccess() {
        Toast.makeText(
            requireContext(),
            "Tarefa criada com sucesso!",
            Toast.LENGTH_LONG
        ).show()
        inputTask?.text = Editable.Factory.getInstance().newEditable("")
        viewModel.refresh()
    }

    private fun onCreationFailure() {
        Toast.makeText(
            requireContext(),
            "Ocorreu um erro ao criar a tarefa",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun onCompleteSuccess() {
        Toast.makeText(
            requireContext(),
            "Tarefa realizada com sucesso!",
            Toast.LENGTH_LONG
        ).show()
        inputTask?.text = Editable.Factory.getInstance().newEditable("")
        viewModel.refresh()
    }

    private fun onCompleteFailure() {
        Toast.makeText(
            requireContext(),
            "Ocorreu um erro ao finalizar a tarefa",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun onTaskCheckboxClicked(task: Task) {
        viewModel.complete(task)
    }

    private fun onSaveClicked(taskText: String) {
        viewModel.setTaskTitle(taskText)
        CreateTaskBottomSheet.newInstance(
            taskTitle = taskText,
            onCreateButtonClicked = { viewModel.save() },
            onPrioritySelected = { viewModel.setSelectedPriority(it) },
            onShiftSelected = { viewModel.setSelectedShift(it) }
        )
            .show(
                parentFragmentManager,
                CreateTaskBottomSheet.TAG
            )
    }
}