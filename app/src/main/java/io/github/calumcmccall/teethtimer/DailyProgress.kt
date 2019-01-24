package io.github.calumcmccall.teethtimer

import android.content.Context
import android.widget.ProgressBar
import android.widget.Toast

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
}