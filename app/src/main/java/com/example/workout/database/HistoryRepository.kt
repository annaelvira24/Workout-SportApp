package com.example.workout.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.util.*

class HistoryRepository(private val historyDao: HistoryDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val historyLogs: Flow<List<History>> = historyDao.getHistoryOnDate()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(history: History) {
        historyDao.insert(history)
    }
}