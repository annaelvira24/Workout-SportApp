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


    fun setExactAlarm(timeInMillis: Long, finish : Long, exercise_type : String, target: String) {
        setAlarm(
                timeInMillis,
                getPendingIntent(
                        getIntent().apply {
                            action = Constants.ACTION_SET_EXACT
                            putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                            putExtra(Constants.EXERCISE_TYPE, exercise_type)
                            putExtra(Constants.TARGET,target)
                            putExtra(Constants.FINISH,finish)
                        }
                )
        )
    }

    fun setDoneAlarm(timeInMillis: Long, exercise_type : String) {
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getSecondIntent().apply {
                    action = Constants.ACTION_SET_EXACT
                    putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                    putExtra(Constants.EXERCISE_TYPE, exercise_type)

                }
            )
        )
    }

    //1 Week
    fun setRepetitiveAlarm(timeInMillis: Long,finish : Long, exercise_type : String, target: String) {
        setAlarm(
                timeInMillis,
                getPendingIntent(
                        getIntent().apply {
                            action = Constants.ACTION_SET_REPETITIVE_EXACT
                            putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                            putExtra(Constants.EXERCISE_TYPE, exercise_type)
                            putExtra(Constants.TARGET,target)
                            putExtra(Constants.FINISH,finish)
                        }
                )
        )
    }

    fun setRepetitiveAlarmOnDays(calendar: Calendar, finishDate: Calendar, exercise_type : String, target: String, repeat:Int) {
        val day = calendar.get(Calendar.DAY_OF_WEEK)

        do {
            if(repeat>=1000000 && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                setRepetitiveAlarm(calendar.timeInMillis,finishDate.timeInMillis , exercise_type,target)
            }
            else if(repeat%1000000>=100000 && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
                setRepetitiveAlarm(calendar.timeInMillis,finishDate.timeInMillis , exercise_type,target)
            }
            else if(repeat%100000>=10000&& calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
                setRepetitiveAlarm(calendar.timeInMillis,finishDate.timeInMillis , exercise_type,target)
            }
            else if(repeat%10000>=1000&& calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
                setRepetitiveAlarm(calendar.timeInMillis,finishDate.timeInMillis , exercise_type,target)
            }
            else if(repeat%1000>=100 && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
                setRepetitiveAlarm(calendar.timeInMillis,finishDate.timeInMillis , exercise_type,target)
            }
            else if(repeat%100>=10 && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
                setRepetitiveAlarm(calendar.timeInMillis,finishDate.timeInMillis , exercise_type,target)
            }
            else if(repeat%10>=1 && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                setRepetitiveAlarm(calendar.timeInMillis,finishDate.timeInMillis , exercise_type,target)
            }
            calendar.add(Calendar.DATE,1)
            finishDate.add(Calendar.DATE,1)

        }while(day != calendar.get(Calendar.DAY_OF_WEEK))

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
    private fun getSecondIntent() = Intent(context, AlarmReceiverDone::class.java)

    private fun getRandomRequestCode() = RandomUtil.getRandomInt()

}