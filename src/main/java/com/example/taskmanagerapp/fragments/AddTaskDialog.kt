package com.example.taskmanagerapp.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.taskmanagerapp.R
import com.example.taskmanagerapp.adapter.TaskItem

class AddTaskDialog : DialogFragment() {

    companion object {
        private const val TAG = "AddTaskDialog"
        fun newInstance() = AddTaskDialog()
    }

    // Callback interface to pass the new task back to TaskFragment
    interface OnTaskAddedListener {
        fun onTaskAdded(task: TaskItem)
    }

    private var listener: OnTaskAddedListener? = null

    fun setOnTaskAddedListener(l: OnTaskAddedListener) {
        this.listener = l
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "onCreateDialog() called")

        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_add_task, null)
        val etTaskTitle = view.findViewById<EditText>(R.id.etTaskTitle)

        return AlertDialog.Builder(requireContext())
            .setTitle("Add New Task")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                val title = etTaskTitle.text.toString().trim()
                Log.d(TAG, "Add button clicked — title entered: '$title'")
                if (title.isEmpty()) {
                    Toast.makeText(requireContext(), "Task title cannot be empty", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "Validation failed — empty title")
                } else {
                    val newTask = TaskItem(
                        id = System.currentTimeMillis().toInt(),
                        title = title,
                        completed = false
                    )
                    listener?.onTaskAdded(newTask)
                    Log.d(TAG, "Task added: $title")
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                Log.d(TAG, "Dialog cancelled")
                dialog.dismiss()
            }
            .create()
    }
}
