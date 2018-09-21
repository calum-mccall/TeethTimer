package com.example.calum.teethtimer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import java.util.*

class SetAlarm {

    val MORNING_REQUEST_CODE = 505
    val EVENING_REQUEST_CODE = 606

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    fun setAlarm(context: Context, hour: Int, minute: Int, morningOrEvening: String) {

        if (morningOrEvening == "Morning") {
            alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, MORNING_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        } else if (morningOrEvening == "Evening") {
            alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, EVENING_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        }

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        alarmMgr?.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
        )

        Toast.makeText(context, morningOrEvening + " Alarm set", Toast.LENGTH_SHORT).show()
        Log.i(TAG, morningOrEvening + " Alarm set for: " + hour + ":" + minute)
    }
}