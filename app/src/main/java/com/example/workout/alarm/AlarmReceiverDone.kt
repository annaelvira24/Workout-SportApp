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

class AlarmReceiverDone: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME, 0L)
        val exercise_type = intent.getStringExtra(Constants.EXERCISE_TYPE)
        when (intent.action) {
            Constants.ACTION_SET_EXACT -> {
                buildNotification(context, "Done " + exercise_type, convertDate(timeInMillis))
            }

        }
    }

    private fun buildNotification(context: Context, title: String,  message: String) {
        Notify
            .with(context)
            .content {
                this.title = title
                text = "Lets Take a Rest  - $message"
            }
            .show()
    }


    private fun convertDate(timeInMillis: Long): String =
        DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()
}