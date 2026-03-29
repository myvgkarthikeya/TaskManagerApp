package com.example.taskmanagerapp.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val TAG = "ReminderWorker"
        const val WORK_NAME = "TaskReminderWork"
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork() started — background reminder triggered")
        Log.d(TAG, "⏰ Reminder: Don't forget to check your tasks!")
        Log.d(TAG, "doWork() completed successfully")
        return Result.success()
    }
}
