package com.example.workout.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import java.sql.Date

class HistoryRepository(private val historyDao: HistoryDao) {
    public var inputDate: Date? = null

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    private val allHistoryByDates = MutableLiveData<List<History>>()
    val historyLogs: LiveData<List<History>> = allHistoryByDates


    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(history: History) {
        historyDao.insert(history)
    }

//    @WorkerThread
//    suspend fun historyByDates(inputDate: Date){
//        allHistoryByDates.postValue(historyDao.getHistoryOnDate(inputDate))
//    }

    fun setDate(date: Date){
        inputDate = date
    }
}