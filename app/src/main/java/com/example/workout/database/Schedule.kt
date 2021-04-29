package com.example.workout.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

@Entity(tableName = "schedule_table")

class Schedule(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "exercise_type") val exercise_type: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time_start") val timeStart: Time,
    @ColumnInfo(name = "time_finish") val timeFinish: Time,
    @ColumnInfo(name = "repeat") val repeat: String?,
    @ColumnInfo(name = "auto_track") val autoTrack: Boolean,
    @ColumnInfo(name = "target") val measure: Float
){
    constructor(exercise_type: String,date: String,timeStart: Time,timeFinish: Time,repeat: String?,autoTrack: Boolean,measure: Float): this(0, exercise_type,date,timeStart,timeFinish,repeat,autoTrack,measure)
}