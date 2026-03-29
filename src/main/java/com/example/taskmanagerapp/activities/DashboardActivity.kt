package com.example.taskmanagerapp.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.taskmanagerapp.databinding.ActivityDashboardBinding
import com.example.taskmanagerapp.fragments.TaskFragment
import com.example.taskmanagerapp.worker.ReminderWorker
import java.util.concurrent.TimeUnit

class DashboardActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "DashboardActivity"
    }

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve username from Intent
        val username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME) ?: "User"
        Log.d(TAG, "Username received from Intent: $username")

        binding.tvWelcome.text = "Welcome, $username!"

        // Load TaskFragment into the container
        if (savedInstanceState == null) {
            Log.d(TAG, "Loading TaskFragment into container")
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, TaskFragment.newInstance())
                .commit()
        }

        scheduleReminderWork()
    }

    private fun scheduleReminderWork() {
        Log.d(TAG, "scheduleReminderWork() called — scheduling periodic reminder every 15 min")

        val reminderRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            ReminderWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, // Don't replace if already scheduled
            reminderRequest
        )

        Log.d(TAG, "scheduleReminderWork() — WorkManager periodic task enqueued")
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
