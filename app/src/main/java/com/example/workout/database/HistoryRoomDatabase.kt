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

@Database(entities = arrayOf(History::class, Schedule::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)

public abstract class HistoryRoomDatabase : RoomDatabase() {

    abstract fun HistoryDao(): HistoryDao
    abstract fun ScheduleDao(): ScheduleDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: HistoryRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): HistoryRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryRoomDatabase::class.java,
                    "history_database"
                ).addCallback(HistoryDatabaseCallback(scope)
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class HistoryDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase1(database.HistoryDao())
                    populateDatabase2(database.ScheduleDao())
                }
            }
        }

        suspend fun populateDatabase1(historyDao: HistoryDao) {
            // Delete all content here.
            historyDao.deleteAll()

            val format = SimpleDateFormat("yyyy-MM-dd")
            val date = format.format(Date(System.currentTimeMillis()))
            val time = Timestamp(System.currentTimeMillis()+3600)
            println(time)
            // Add dummy data
            var history = History(0,"Walking", "2021-04-28", time, time, 1000.0f)
            historyDao.insert(history)

            history = History(0,"Cycling", "2021-04-28", time, time, 1000.0f)
            historyDao.insert(history)
        }

        suspend fun populateDatabase2(scheduleDao: ScheduleDao) {
            // Delete all content here.
            scheduleDao.deleteAll()

            val format = SimpleDateFormat("yyyy-MM-dd")
            val date = format.format(Date(System.currentTimeMillis()))
            val time = Time(System.currentTimeMillis())
            println(time)
            // Add dummy data
            var schedule = Schedule(0,"Walking", "2021-05-02", time, time, "0100000", false,1000.0f)
            scheduleDao.insert(schedule)

            schedule = Schedule(0,"Walking", "2021-05-01", time, time, "0000000", true, 2000.0f)
            scheduleDao.insert(schedule)

        }
    }
}