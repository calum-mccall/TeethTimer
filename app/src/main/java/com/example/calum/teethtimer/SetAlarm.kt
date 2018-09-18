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

class SetAlarm {

    var REQUEST_CODE = 2

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    fun setAlarm(context: Context) {
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        alarmMgr?.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 10 * 1000,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                alarmIntent
        )
        Toast.makeText(context, "Alarm set", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "Alarm set")
    }
}