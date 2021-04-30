package com.example.workout.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_table")
data class Exercise(
        var exercise: Int = 0,
        var timestamp: Long = 0L,
        var distanceInMeters: Int = 0,
        var timeInMillis: Long = 0L,
        var stepsAmount: Int = 0,
//        var img: Bitmap? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}