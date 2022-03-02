package com.example.todo.ui.creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.example.todo.data.models.TaskPriority
import com.example.todo.data.models.TaskShift
import com.example.todo.databinding.FragmentCreateTaskBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class CreateTaskBottomSheet(
    private var taskTitleInput: String,
    private var onCreateButtonClicked: () -> Unit,
    private var onPrioritySelected: (Int) -> Unit,
    private var onShiftSelected: (Int) -> Unit
) : BottomSheetDialogFragment() {

    companion object {

        const val TAG = "CreateTaskBottomSheet"

        fun newInstance(
            taskTitle: String,
            onCreateButtonClicked: () -> Unit,
            onPrioritySelected: (Int) -> Unit,
            onShiftSelected: (Int) -> Unit
        ): CreateTaskBottomSheet {
            return CreateTaskBottomSheet(
                taskTitleInput = taskTitle,
                onCreateButtonClicked = onCreateButtonClicked,
                onPrioritySelected = onPrioritySelected,
                onShiftSelected = onShiftSelected
            )
        }
    }

    private lateinit var binding: FragmentCreateTaskBottomSheetBinding

    private var taskTitle: TextView? = null
    private var prioritySpinner: Spinner? = null
    private var shiftSpinner: Spinner? = null
    private var createTaskButton: Button? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTaskBottomSheetBinding.inflate(inflater, container, false)

        taskTitle = binding.taskTitle
        prioritySpinner = binding.prioritySpinner
        shiftSpinner = binding.shiftSpinner
        createTaskButton = binding.createTaskButton

        return binding.root
    }

    private fun setupViews() {
        taskTitle?.text = taskTitleInput

        with(prioritySpinner) {
            val items = TaskPriority.values().map { it.display }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                items
            )
            this?.adapter = adapter
        }

        with(shiftSpinner) {
            val items = TaskShift.values().map { it.display }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                items
            )
            this?.adapter = adapter
        }

        createTaskButton?.setOnClickListener {
            onCreateClicked()
        }
    }

    private fun onCreateClicked() {
        onPrioritySelected(this.prioritySpinner?.selectedItemPosition ?: 0)
        onShiftSelected(this.shiftSpinner?.selectedItemPosition ?: 0)
        onCreateButtonClicked()
        dismiss()
    }

}