package io.github.calumcmccall.teethtimer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun setMorningAlarm(view: View) {
        var morningTimePickerFragment = TimePickerFragment()
        morningTimePickerFragment.show(supportFragmentManager, getString(R.string.morning_time_picker))
    }

    fun setEveningAlarm(view: View) {
        var eveningTimePickerFragment = TimePickerFragment()
        eveningTimePickerFragment.show(supportFragmentManager, getString(R.string.evening_time_picker))
    }

    fun showSetAlarms(sharedPreferences: SharedPreferences) {
        textView_morning_alarm.text = sharedPreferences.getString(R.string.morning_alarm_time.toString(), morningAlarm)
        textView_evening_alarm.text = sharedPreferences.getString(R.string.evening_alarm_time.toString(), eveningAlarm)

        button_delete_morning_alarm.isEnabled = sharedPreferences.getBoolean(R.bool.morning_alarm_set.toString(), false)
        button_delete_evening_alarm.isEnabled = sharedPreferences.getBoolean(R.bool.evening_alarm_set.toString(), false)
    }

    fun deleteMorningAlarm(view: View) {
        var deleteMorningAlarmDialog = DeleteAlarmDialogFragment()
        deleteMorningAlarmDialog.show(fragmentManager, getString(R.string.delete_morning_alarm_dialog))
    }

    fun deleteEveningAlarm(view: View) {
        var deleteEveningAlarmDialog = DeleteAlarmDialogFragment()
        deleteEveningAlarmDialog.show(fragmentManager, getString(R.string.delete_evening_alarm_dialog))
    }
}
