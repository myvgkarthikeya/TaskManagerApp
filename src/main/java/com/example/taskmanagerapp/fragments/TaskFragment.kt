package com.example.taskmanagerapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagerapp.activities.WebActivity
import com.example.taskmanagerapp.adapter.TaskAdapter
import com.example.taskmanagerapp.adapter.TaskItem
import com.example.taskmanagerapp.api.RetrofitInstance
import com.example.taskmanagerapp.databinding.FragmentTaskBinding
import kotlinx.coroutines.launch

class TaskFragment : Fragment() {

    companion object {
        private const val TAG = "TaskFragment"
        fun newInstance() = TaskFragment()
    }

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        setupRecyclerView()
        setupButtons()
        fetchTasksFromApi()
    }

    private fun setupRecyclerView() {
        Log.d(TAG, "setupRecyclerView() called")
        taskAdapter = TaskAdapter()
        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }
    }

    private fun setupButtons() {
        Log.d(TAG, "setupButtons() called")

        // Add Task button — shows the DialogFragment
        binding.btnAddTask.setOnClickListener {
            Log.d(TAG, "btnAddTask clicked")
            val dialog = AddTaskDialog.newInstance()
            dialog.setOnTaskAddedListener(object : AddTaskDialog.OnTaskAddedListener {
                override fun onTaskAdded(task: TaskItem) {
                    taskAdapter.addTask(task)
                    Log.d(TAG, "New task received from dialog: ${task.title}")
                }
            })
            dialog.show(parentFragmentManager, "AddTaskDialog")
        }

        // Open Web button — opens WebActivity
        binding.btnOpenWeb.setOnClickListener {
            Log.d(TAG, "btnOpenWeb clicked — launching WebActivity")
            val intent = Intent(requireContext(), WebActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchTasksFromApi() {
        Log.d(TAG, "fetchTasksFromApi() called")
        binding.progressBar.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val todos = RetrofitInstance.api.getTodos()
                Log.d(TAG, "fetchTasksFromApi() — received ${todos.size} todos from API")

                // Take first 20 to keep list manageable
                val taskItems = todos.take(20).map { todo ->
                    TaskItem(id = todo.id, title = todo.title, completed = todo.completed)
                }

                taskAdapter.setTasks(taskItems)
                binding.progressBar.visibility = View.GONE
                Log.d(TAG, "fetchTasksFromApi() — RecyclerView updated")
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                Log.d(TAG, "fetchTasksFromApi() error: ${e.message}")
                Toast.makeText(requireContext(), "Failed to load tasks: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView() called")
        _binding = null
    }
}
