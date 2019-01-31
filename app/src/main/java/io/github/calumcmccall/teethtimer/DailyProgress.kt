package io.github.calumcmccall.teethtimer

import android.content.Context
import android.text.format.DateUtils
import android.widget.ProgressBar
import android.widget.Toast
import java.time.Month
import java.time.Year
import java.util.*
import java.util.Calendar.MONTH


class DailyProgress {

    var daily = 0

    fun brushed(context: Context, progressBar: ProgressBar) {
        progressBar.incrementProgressBy(50)

        currentProgress(context, progressBar)
    }

    fun currentProgress(context: Context, progressBar: ProgressBar) {
        val sharedPreferences = context?.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)

        var daily_progress = progressBar.progress

        sharedPreferences.edit().putInt(R.string.current_progress.toString(), daily_progress).apply()
    }

    fun fillCurrent(context: Context, progressBar: ProgressBar) {
        val sharedPreferences = context?.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)

        var day = sharedPreferences.getInt(R.string.current_progress.toString(), daily)

        progressBar.incrementProgressBy(day.toInt())
    }

    fun sameDay(context: Context, progressBar: ProgressBar) {
        //Get current day and compare to day saved in shared preferences, if same then fill current. If different current to 0 and replace current day
        val sharedPreferences = context?.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)

        var c = Calendar.getInstance()
        var now = c.get(Calendar.DAY_OF_YEAR)
        var day_check = sharedPreferences.getString(R.string.current_day.toString(), "0")

        if (now == day_check.toInt()) {
            fillCurrent(context, progressBar)
        } else {
            sharedPreferences.edit().putString(R.string.current_day.toString(), now.toString()).apply()
            sharedPreferences.edit().putString(R.string.current_progress.toString(), "0")
        }
    }
}