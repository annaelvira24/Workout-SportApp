package com.example.workout.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.workout.database.Converters

@Database(
    entities = [Exercise::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ExerciseDatabase : RoomDatabase() {

    abstract fun getExerciseDao(): ExerciseDAO
}