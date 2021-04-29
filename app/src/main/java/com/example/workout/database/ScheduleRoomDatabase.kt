package com.example.workout.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate

@Database(entities = arrayOf(Schedule::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)

public abstract class ScheduleRoomDatabase : RoomDatabase() {

    abstract fun ScheduleDao(): ScheduleDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ScheduleRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ScheduleRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScheduleRoomDatabase::class.java,
                    "history_database"
                ).addCallback(ScheduleDatabaseCallback(scope)
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class ScheduleDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.ScheduleDao())
                }
            }
        }

        suspend fun populateDatabase(scheduleDao: ScheduleDao) {
            // Delete all content here.
            scheduleDao.deleteAll()

            val format = SimpleDateFormat("yyyy-MM-dd")
            val date = format.format(Date(System.currentTimeMillis()))
            val time = Time(System.currentTimeMillis())
            println(time)
            // Add dummy data
            var schedule = Schedule(0,"Walking", date, time, time, "0100000", false,1000.0f)
            scheduleDao.insert(schedule)

            schedule = Schedule(0,"Walking", "2021-05-01", time, time, "0100000", true, 2000.0f)
            scheduleDao.insert(schedule)

        }
    }
}