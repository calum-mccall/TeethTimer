package com.example.calum.teethtimer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View

import kotlinx.android.synthetic.main.activity_alarms.*
import kotlinx.android.synthetic.main.content_alarms.*

class AlarmsActivity : AppCompatActivity() {

    val TAG = "Alarms Activity"

    var morningAlarm = ""
    var eveningAlarm = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarms)
        setSupportActionBar(toolbar)

        val sharedPreferences = this.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)
        showSetAlarms(sharedPreferences)
    }

    fun setMorningAlarm(view: View) {
        var morningTimePickerFragment = TimePickerFragment()
        morningTimePickerFragment.show(supportFragmentManager, getString(R.string.morning_time_picker))
        Log.i(TAG, "Showing timePicker for morning alarm")
    }

    fun setEveningAlarm(view: View) {
        var eveningTimePickerFragment = TimePickerFragment()
        eveningTimePickerFragment.show(supportFragmentManager, getString(R.string.evening_time_picker))
        Log.i(TAG, "Showing timePicker for evening alarm")
    }

    fun showSetAlarms(sharedPreferences: SharedPreferences) {
        textView_morning_alarm.text = sharedPreferences.getString(R.string.morning_alarm_time.toString(), morningAlarm)
        textView_evening_alarm.text = sharedPreferences.getString(R.string.evening_alarm_time.toString(), eveningAlarm)
    }

    fun deleteAlarms(view: View) {
        var deleteAlarmDialog = DeleteAlarmDialogFragment()
        deleteAlarmDialog.show(fragmentManager, getString(R.string.delete_alarm_dialog))
        Log.i(TAG, "Showing dialog for deleting alarm")
    }
}
