package com.example.taskmanagerapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagerapp.R

data class TaskItem(
    val id: Int,
    val title: String,
    var completed: Boolean = false
)

class TaskAdapter(
    private val tasks: MutableList<TaskItem> = mutableListOf()
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    companion object {
        private const val TAG = "TaskAdapter"
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTaskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val cbCompleted: CheckBox = itemView.findViewById(R.id.cbCompleted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        Log.d(TAG, "onCreateViewHolder() called")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        Log.d(TAG, "onBindViewHolder() — binding task id=${task.id}, title=${task.title}")
        holder.tvTaskTitle.text = task.title
        holder.cbCompleted.isChecked = task.completed
        holder.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
            task.completed = isChecked
            Log.d(TAG, "Task '${task.title}' marked completed=$isChecked")
        }
    }

    override fun getItemCount(): Int = tasks.size

    // Add a single task and notify adapter
    fun addTask(task: TaskItem) {
        Log.d(TAG, "addTask() — adding: ${task.title}")
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }

    // Replace all tasks (e.g. from API)
    fun setTasks(newTasks: List<TaskItem>) {
        Log.d(TAG, "setTasks() — loading ${newTasks.size} tasks")
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }
}
