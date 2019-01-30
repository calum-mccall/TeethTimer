package io.github.calumcmccall.teethtimer

import android.content.Context
import android.widget.ProgressBar
import android.widget.Toast
import java.time.Year
import java.util.*

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
        Toast.makeText(context, day.toString(), Toast.LENGTH_SHORT).show()
    }

    fun sameDay(context: Context, progressBar: ProgressBar) {
        //Get current day and compare to day saved in shared preferences, if same then fill current. If different current to 0 and replace current day
        val sharedPreferences = context?.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)

        var now = Calendar.DAY_OF_MONTH
        var day_check = sharedPreferences.getString(R.string.current_day.toString(), now.toString())

        if (day_check.equals(now.toString())) {
            fillCurrent(context, progressBar)
            Toast.makeText(context, "Checking Same Day", Toast.LENGTH_SHORT).show()
        } else {
            sharedPreferences.edit().putString(R.string.current_day.toString(), now.toString()).apply()
            sharedPreferences.edit().putInt("0", 0)
        }
    }
}