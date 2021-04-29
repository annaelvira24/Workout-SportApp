package com.example.workout.database

import androidx.room.TypeConverter
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

class Converters {
    @TypeConverter
    fun fromTimestampToDate(value: Long?): Timestamp? {
        return value?.let { Timestamp(it) }
    }

    @TypeConverter
    fun fromTime(value: Long?): Time? {
        return value?.let { Time(it) }
    }

    @TypeConverter
    fun dateToTimestamp(timestamp: Timestamp?): Long? {
        return timestamp?.time?.toLong()
    }

    @TypeConverter
    fun timeToTimestamp(time: Time?): Long? {
        return time?.time?.toLong()
    }
}
