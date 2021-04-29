package com.example.workout.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.sql.Date

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM schedule_table ORDER BY id ASC")
    fun getAllSchedule(): LiveData<List<Schedule>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(schedule: Schedule)

    @Query("DELETE FROM schedule_table")
    suspend fun deleteAll()
}