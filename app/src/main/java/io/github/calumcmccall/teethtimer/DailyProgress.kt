package io.github.calumcmccall.teethtimer

import android.content.Context
import android.text.format.DateUtils
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.time.Month
import java.time.Year
import java.util.*
import java.util.Calendar.MONTH


class DailyProgress {

    var daily = 0

    fun brushed(context: Context, progressBar: ProgressBar) {
        if (progressBar.progress < 100) {
            progressBar.incrementProgressBy(50)
            currentProgress(context, progressBar)
        }

        if (progressBar.progress == 100) {
            completed(context)
        }
    }

    fun currentProgress(context: Context, progressBar: ProgressBar) {
        val sharedPreferences = context?.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)

        var daily_progress = progressBar.progress

        sharedPreferences.edit().putInt(R.string.current_progress.toString(), daily_progress).apply()
    }

    fun fillCurrent(context: Context, progressBar: ProgressBar) {
        val sharedPreferences = context?.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)

        val day = sharedPreferences.getInt(R.string.current_progress.toString(), daily)

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
        } else if (now - day_check.toInt() == 1) {
            Toast.makeText(context, "Streak", Toast.LENGTH_SHORT).show()
            resetProgress(context, progressBar, now.toString())
        } else {
            resetProgress(context, progressBar, now.toString())
            sharedPreferences.edit().putInt(R.string.streak.toString(), 0).apply()
        }
    }

    fun completed(context: Context) {
        val sharedPreferences = context?.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)

        var completedToday = sharedPreferences.getBoolean(R.string.completed_today.toString(), false)

        if (completedToday == false) {

            var streak = sharedPreferences.getInt(R.string.streak.toString(), 0)

            streak = streak + 1

            sharedPreferences.edit().putInt(R.string.streak.toString(), streak).apply()

            sharedPreferences.edit().putBoolean(R.string.completed_today.toString(), true).apply()

            Toast.makeText(context, "Completed " + streak, Toast.LENGTH_SHORT).show()
        }
    }

    fun resetProgress(context: Context, progressBar: ProgressBar, now: String) {
        val  sharedPreferences = context?.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)

        sharedPreferences.edit().putString(R.string.current_day.toString(), now).apply()
        sharedPreferences.edit().putString(R.string.current_progress.toString(), "0").apply()
        sharedPreferences.edit().putBoolean(R.string.completed_today.toString(), false).apply()
    }

    fun displayStreak (context: Context, textView: TextView) {
        val sharedPreferences = context?.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)

        val streak = sharedPreferences.getInt(R.string.streak.toString(), 0)

        textView.text = streak.toString()
    }
}