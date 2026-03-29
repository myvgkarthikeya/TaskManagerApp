# Retrofit
-keepattributes Signature
-keepattributes Exceptions
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Gson
-keep class com.google.gson.** { *; }
-keep class com.example.taskmanagerapp.api.** { *; }

# DataStore
-keep class androidx.datastore.** { *; }

# WorkManager
-keep class androidx.work.** { *; }
-keep class com.example.taskmanagerapp.worker.** { *; }
