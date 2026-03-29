package com.example.taskmanagerapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.taskmanagerapp.databinding.ActivityLoginBinding
import com.example.taskmanagerapp.datastore.DataStoreManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LoginActivity"
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(applicationContext)

        // Check if username is already saved — auto-navigate to Dashboard
        lifecycleScope.launch {
            dataStoreManager.usernameFlow.collect { savedUsername ->
                if (!savedUsername.isNullOrEmpty()) {
                    Log.d(TAG, "Saved username found: $savedUsername — navigating to Dashboard")
                    navigateToDashboard(savedUsername)
                    return@collect
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            Log.d(TAG, "btnLogin clicked")
            val username = binding.etUsername.text.toString().trim()

            if (username.isEmpty()) {
                Log.d(TAG, "Validation failed — username is empty")
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                binding.etUsername.error = "Please enter your username"
                return@setOnClickListener
            }

            Log.d(TAG, "Username valid: $username — saving to DataStore")

            lifecycleScope.launch {
                dataStoreManager.saveUsername(username)
                Log.d(TAG, "Username saved — navigating to Dashboard")
                navigateToDashboard(username)
            }
        }
    }

    private fun navigateToDashboard(username: String) {
        Log.d(TAG, "navigateToDashboard() called with username: $username")
        val intent = Intent(this, DashboardActivity::class.java).apply {
            putExtra(EXTRA_USERNAME, username)
        }
        startActivity(intent)
        finish() // Remove LoginActivity from back stack
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}
