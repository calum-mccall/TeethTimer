package io.github.calumcmccall.teethtimer

import android.widget.ProgressBar

class DailyProgress {

    fun brushed(progressBar: ProgressBar) {
        progressBar.incrementProgressBy(50)
    }
}