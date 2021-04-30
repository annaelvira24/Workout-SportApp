package com.example.workout.repo

import com.example.workout.database.Exercise
import com.example.workout.database.ExerciseDAO
import javax.inject.Inject

class MainRepo @Inject constructor(
    val exerciseDao: ExerciseDAO
) {
    suspend fun insertExercise(exercise: Exercise) = exerciseDao.insertExercise(exercise)

    suspend fun deleteExercise(exercise: Exercise) = exerciseDao.deleteExercise(exercise)

    fun getExerciseByDate(date: Long) = exerciseDao.getExerciseByDate(date)

    fun nukeTable() = exerciseDao.nukeTable()
}