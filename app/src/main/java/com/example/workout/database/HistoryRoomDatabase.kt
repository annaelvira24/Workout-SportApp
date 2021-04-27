package com.example.workout.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDate
import java.util.*

@Database(entities = arrayOf(History::class), version = 1, exportSchema = false)
public abstract class HistoryRoomDatabase : RoomDatabase() {

    abstract fun HistoryDao(): HistoryDao

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
                    populateDatabase(database.HistoryDao())
                }
            }
        }

        suspend fun populateDatabase(historyDao: HistoryDao) {
            // Delete all content here.
//            wordDao.deleteAll()

            val date = Date(2021, 4, 1)
            val time = Timestamp(System.currentTimeMillis())
            // Add sample words.
            var history = History(1, "Walking", date, time, time, 10.0f, null)
            historyDao.insert(history)
//            word = Word("World!")
//            wordDao.insert(word)

        }
    }
}