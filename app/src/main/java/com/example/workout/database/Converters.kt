package com.example.workout.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import org.json.JSONArray
import org.json.JSONException
import java.io.ByteArrayOutputStream
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
    fun fromArrayListOfDoubles(list: ArrayList<Double>?): String {
        return list?.joinToString(separator = ";") { it.toString() } ?: ""
    }

    @TypeConverter
    fun toArrayListOfDoubles(string: String?): ArrayList<Double> {
        return ArrayList(string?.split(";")?.mapNotNull { it.toDoubleOrNull() } ?: emptyList())
    }

    @TypeConverter
    fun dateToTimestamp(timestamp: Timestamp?): Long? {
        return timestamp?.time?.toLong()
    }

    @TypeConverter
    fun timeToTimestamp(time: Time?): Long? {
        return time?.time?.toLong()
    }

    @TypeConverter
    fun toBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    @TypeConverter
    fun fromBitmap(bmp: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}
