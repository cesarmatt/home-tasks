package com.example.todo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.models.Task
import com.example.todo.databinding.ItemTaskBinding

class HomeAdapter(
    private val onTaskChecked: (Task) -> Unit
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var tasks: List<Task> = listOf()

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size

    inner class ViewHolder(private val itemBinding: ItemTaskBinding): RecyclerView.ViewHolder(itemBinding.root) {

        val taskCheckbox: CheckBox = itemBinding.taskCheckbox

        fun bind(task: Task) {
            itemBinding.taskTitle.text = task.title
            taskCheckbox.setOnCheckedChangeListener { compoundButton, isChecked ->
                onTaskChecked(task)
            }
        }
    }
}