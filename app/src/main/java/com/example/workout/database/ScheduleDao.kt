package com.example.workout.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.sql.Date
import java.sql.Time

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM schedule_table ORDER BY id ASC")
    fun getAllSchedule(): LiveData<List<Schedule>>

    @Query("SELECT * FROM schedule_table WHERE id = :id")
    fun getSchedule(id: Int): Schedule

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(schedule: Schedule)

    @Query("DELETE FROM schedule_table WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("UPDATE schedule_table SET exercise_type = :exercise_type, date = :date, time_start = :timeStart, time_finish = :timeFinish, repeat = :repeat, auto_track = :autoTrack,target = :measure WHERE id = :id ")
    suspend fun update(id : Int, exercise_type : String, date: String, timeStart: Time, timeFinish: Time, repeat: String?, autoTrack: Boolean, measure: Float)

    @Query("DELETE FROM schedule_table")
    suspend fun deleteAll()
}