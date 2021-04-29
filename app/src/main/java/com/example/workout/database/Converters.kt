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
    fun fromTimestampToDate2(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(timestamp: Timestamp?): Long? {
        return timestamp?.time?.toLong()
    }

    @TypeConverter
    fun dateToTimestamp2(date: Date?): Long? {
        return date?.time?.toLong()
    }
}
