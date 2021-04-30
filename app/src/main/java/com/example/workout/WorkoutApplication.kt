package com.example.workout

import android.app.Application
import com.example.workout.database.HistoryRepository
import com.example.workout.database.HistoryRoomDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

@HiltAndroidApp
class WorkoutApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { HistoryRoomDatabase.getDatabase(this, applicationScope) }
    val historyDao by lazy { database.HistoryDao() }
    val scheduleDao by lazy { database.ScheduleDao() }

}