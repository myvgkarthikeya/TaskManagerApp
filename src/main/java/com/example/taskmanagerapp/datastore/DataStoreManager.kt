package com.example.taskmanagerapp.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property to create DataStore instance tied to application context
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
        private const val TAG = "DataStoreManager"
    }

    // Save username to DataStore
    suspend fun saveUsername(username: String) {
        Log.d(TAG, "saveUsername() called with: $username")
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
        Log.d(TAG, "saveUsername() completed successfully")
    }

    // Read username as Flow from DataStore
    val usernameFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            val username = preferences[USERNAME_KEY]
            Log.d(TAG, "usernameFlow emitting: $username")
            username
        }
}
