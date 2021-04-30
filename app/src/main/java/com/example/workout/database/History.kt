package com.example.workout.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.io.Serializable
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

@Entity(tableName = "history_table")

class History(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "exercise_type") val exercise_type: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time_start") val timeStart: Timestamp,
    @ColumnInfo(name = "time_finish") val timeFinish: Timestamp,
    @ColumnInfo(name = "measure") val measure: Float
//    @ColumnInfo(name = "track") val track: List<Float>?


) : Serializable