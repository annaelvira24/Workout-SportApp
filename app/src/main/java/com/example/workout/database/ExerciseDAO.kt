package com.example.workout.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExerciseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM exercise_table WHERE timestamp = :date")
    fun getExerciseByDate(date: Long): LiveData<List<Exercise>>

    @Query("DELETE FROM exercise_table")
    fun nukeTable()

}