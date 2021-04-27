package com.example.workout

import android.app.Application
import com.example.workout.database.HistoryRepository
import com.example.workout.database.HistoryRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WorkoutApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { HistoryRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { HistoryRepository(database.HistoryDao()) }
}