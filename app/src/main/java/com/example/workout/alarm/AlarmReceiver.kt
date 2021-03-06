package com.example.workout.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import com.example.workout.other.Constants
import io.karn.notify.Notify
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import  com.example.workout.database.Schedule

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME, 0L)
        val exercise_type = intent.getStringExtra(Constants.EXERCISE_TYPE)
        val target = intent.getStringExtra(Constants.TARGET) + if (exercise_type == "Cycling") " km" else " steps"
        val finish = intent.getLongExtra(Constants.FINISH,0L)


        when (intent.action) {
            Constants.ACTION_SET_EXACT -> {
                setDoneAlarm(AlarmService(context),finish,exercise_type)
                buildNotification(context, "Let's " + exercise_type, target, convertDate(timeInMillis))
            }

            Constants.ACTION_SET_REPETITIVE_EXACT -> {
                setDoneAlarm(AlarmService(context),finish,exercise_type)
                setRepetitiveAlarm(AlarmService(context),finish,exercise_type,target)
                buildNotification(context, "Let's " + exercise_type, target, convertDate(timeInMillis))
            }
        }
    }

    private fun buildNotification(context: Context, title: String, target: String, message: String) {
        Notify
                .with(context)
                .content {
                    this.title = title
                    text = "Lets achieve  $target - $message"
                }
                .show()
    }

    private fun setRepetitiveAlarm(alarmService: AlarmService, finish: Long,exercise_type: String, target: String) {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(7)
            Timber.d("Set alarm for next week same time - ${convertDate(this.timeInMillis)}")
        }
        val calFinish = Calendar.getInstance().apply {
            this.timeInMillis = finish + TimeUnit.DAYS.toMillis(7)
            Timber.d("Set alarm for next week same time - ${convertDate(this.timeInMillis)}")
        }
        alarmService.setRepetitiveAlarm(cal.timeInMillis,calFinish.timeInMillis ,exercise_type,target)
    }

    private fun setDoneAlarm(alarmService: AlarmService, finish: Long, exercise_type: String) {
        alarmService.setDoneAlarm(finish,exercise_type)
    }

    private fun convertDate(timeInMillis: Long): String =
            DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()
}

