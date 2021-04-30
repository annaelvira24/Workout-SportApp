package com.example.workout.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.workout.other.Constants
import com.example.workout.other.RandomUtil

import org.jetbrains.annotations.Async

import java.util.*

class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager? =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?


    fun setExactAlarm(timeInMillis: Long, exercise_type : String, target: String) {
        setAlarm(
                timeInMillis,
                getPendingIntent(
                        getIntent().apply {
                            action = Constants.ACTION_SET_EXACT
                            putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                            putExtra(Constants.EXERCISE_TYPE, exercise_type)
                            putExtra(Constants.TARGET,target)
                        }
                )
        )
    }

    //1 Week
    fun setRepetitiveAlarm(timeInMillis: Long, exercise_type : String, target: String) {
        setAlarm(
                timeInMillis,
                getPendingIntent(
                        getIntent().apply {
                            action = Constants.ACTION_SET_REPETITIVE_EXACT
                            putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                            putExtra(Constants.EXERCISE_TYPE, exercise_type)
                            putExtra(Constants.TARGET,target)
                        }
                )
        )
    }

    private fun getPendingIntent(intent: Intent) =
            PendingIntent.getBroadcast(
                    context,
                    getRandomRequestCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )


    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {
        alarmManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        timeInMillis,
                        pendingIntent
                )
            } else {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        timeInMillis,
                        pendingIntent
                )
            }
        }
    }

    private fun getIntent() = Intent(context, AlarmReceiver::class.java)

    private fun getRandomRequestCode() = RandomUtil.getRandomInt()

}