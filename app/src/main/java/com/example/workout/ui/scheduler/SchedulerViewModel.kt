package com.example.workout.ui.history

import androidx.lifecycle.*
import com.example.workout.database.History
import com.example.workout.database.HistoryDao
import com.example.workout.database.Schedule
import com.example.workout.database.ScheduleDao
import kotlinx.coroutines.launch
import java.sql.Time

class SchedulerViewModel(private val scheduleDao: ScheduleDao) : ViewModel() {

    fun getAllSchedule() : LiveData<List<Schedule>>{
        return scheduleDao.getAllSchedule()
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(schedule: Schedule) = viewModelScope.launch {
        scheduleDao.insert(schedule)
    }

    fun update(id : Int, exercise_type : String, date: String, timeStart: Time, timeFinish: Time, repeat: String?, autoTrack: Boolean, measure: Float) = viewModelScope.launch {
        scheduleDao.update(id , exercise_type, date, timeStart, timeFinish, repeat, autoTrack, measure)
    }

    fun delete(id: Int) = viewModelScope.launch {
        scheduleDao.delete(id)
    }

//    fun historyByDates() = viewModelScope.launch {
//        historyDao.setDate(Date(28,4,2021))
//    }
}

class ScheduleViewModelFactory(private val scheduleDao: ScheduleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SchedulerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SchedulerViewModel(scheduleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}