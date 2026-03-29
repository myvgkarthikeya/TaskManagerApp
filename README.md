# TaskManagerApp — Android Studio Project

A complete Android app in Kotlin + XML demonstrating Retrofit, DataStore, RecyclerView, WorkManager, and WebView.

---

## Project Structure

```
TaskManagerApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/taskmanagerapp/
│   │   │   ├── activities/
│   │   │   │   ├── LoginActivity.kt
│   │   │   │   ├── DashboardActivity.kt
│   │   │   │   └── WebActivity.kt
│   │   │   ├── fragments/
│   │   │   │   ├── TaskFragment.kt
│   │   │   │   └── AddTaskDialog.kt
│   │   │   ├── adapter/
│   │   │   │   └── TaskAdapter.kt
│   │   │   ├── api/
│   │   │   │   └── ApiService.kt         (also contains RetrofitInstance + TodoItem)
│   │   │   ├── datastore/
│   │   │   │   └── DataStoreManager.kt
│   │   │   └── worker/
│   │   │       └── ReminderWorker.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_login.xml
│   │   │   │   ├── activity_dashboard.xml
│   │   │   │   ├── activity_web.xml
│   │   │   │   ├── fragment_task.xml
│   │   │   │   ├── item_task.xml
│   │   │   │   └── dialog_add_task.xml
│   │   │   ├── values/
│   │   │   │   ├── strings.xml
│   │   │   │   ├── colors.xml
│   │   │   │   └── themes.xml
│   │   │   └── xml/
│   │   │       └── network_security_config.xml
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
└── gradle.properties
```

---

## How to Run in Android Studio

### Step 1 — Open the Project
1. Launch **Android Studio** (Hedgehog 2023.1.1 or newer recommended)
2. Click **File → Open**
3. Navigate to and select the `TaskManagerApp` root folder
4. Click **OK** and wait for Gradle sync to complete

### Step 2 — Sync Gradle
- Android Studio will auto-sync on open
- If it doesn't, click **File → Sync Project with Gradle Files**
- Wait for the sync bar at the bottom to finish (may take 1–2 minutes on first run to download dependencies)

### Step 3 — Set Up Emulator or Device
**Option A — Emulator:**
1. Click **Tools → Device Manager**
2. Click **Create Device**
3. Choose a phone (e.g. Pixel 6), click Next
4. Select a system image with API Level **24 or higher** (e.g. API 34)
5. Click Finish, then start the emulator with the ▶ button

**Option B — Physical Device:**
1. Enable **Developer Options** on your Android phone (tap Build Number 7 times in Settings → About Phone)
2. Enable **USB Debugging** in Developer Options
3. Connect via USB — Android Studio will detect it automatically

### Step 4 — Run the App
1. Select your emulator or device from the device dropdown at the top
2. Click the **▶ Run** button (or press `Shift+F10`)
3. The app will build and install automatically

---

## Features Walkthrough

### Login Screen
- Enter any non-empty username and tap **Login**
- Username is saved to **DataStore** — on next launch, you skip login automatically

### Dashboard
- Displays "Welcome, [username]!"
- Hosts **TaskFragment** inside a FrameLayout container
- Schedules a **WorkManager** periodic reminder every 15 minutes (visible in Logcat)

### Task List (TaskFragment)
- On load, fetches the first 20 tasks from `https://jsonplaceholder.typicode.com/todos` via **Retrofit**
- Each task shows title + checkbox
- Tap **+ Add Task** to open a **DialogFragment** and add a custom task — it appears instantly in the RecyclerView
- Tap **Open Web** to launch Google in a built-in **WebView**

### WebView
- Opens `https://www.google.com` inside the app
- Back button navigates within WebView history before closing the activity

---

## Viewing Logs (Logcat)

Each major function has `Log.d(TAG, ...)` calls. To view them:

1. Open **Logcat** tab at the bottom of Android Studio
2. Select your device/emulator
3. Filter by tag to focus — examples:
   - `LoginActivity`
   - `DashboardActivity`
   - `TaskFragment`
   - `TaskAdapter`
   - `AddTaskDialog`
   - `RetrofitInstance`
   - `DataStoreManager`
   - `ReminderWorker`
   - `WebActivity`

---

## Dependencies (app/build.gradle)

| Library | Version | Purpose |
|---|---|---|
| Retrofit | 2.9.0 | HTTP API calls |
| Gson Converter | 2.9.0 | JSON parsing |
| OkHttp Logging | 4.12.0 | Network logging |
| DataStore Preferences | 1.0.0 | Persistent key-value storage |
| WorkManager KTX | 2.9.0 | Background task scheduling |
| RecyclerView | 1.3.2 | Scrollable task list |
| Fragment KTX | 1.6.2 | Fragment support |
| Lifecycle KTX | 2.7.0 | ViewModel + coroutine scope |
| Coroutines Android | 1.7.3 | Async operations |
| Material Components | 1.11.0 | UI styling |
| ConstraintLayout | 2.1.4 | Flexible layouts |

---

## Minimum Requirements

- **Android Studio:** Hedgehog (2023.1.1) or newer
- **JDK:** 17 (bundled with Android Studio)
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Internet connection** required for first Gradle sync and for fetching API tasks

---

## Troubleshooting

**Gradle sync fails:**
- Check your internet connection
- Try **File → Invalidate Caches / Restart**

**App crashes on launch:**
- Check Logcat for the error tag
- Ensure the emulator/device has internet access

**Tasks not loading:**
- The app calls `jsonplaceholder.typicode.com` — ensure the emulator has network access
- Check Logcat filter `RetrofitInstance` for errors

**WorkManager reminder not firing immediately:**
- WorkManager periodic tasks have a minimum interval of 15 minutes
- The first execution may be delayed — this is expected Android behavior
- Watch Logcat filter `ReminderWorker` for the log when it does fire
