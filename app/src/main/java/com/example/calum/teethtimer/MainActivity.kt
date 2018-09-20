package com.example.calum.teethtimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var confettiSelector = ConfettiTypes()

    var waveHelper = WaveHelper(null)

    var notifications = Notifications(this)

    var timerRunning = false
    var isPaused = false

    val millisInFuture:Long = 120000
    val countDownInterval:Long = 1000

    var resumeFromMillis:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        waveHelper = WaveHelper(wave)
    }

    //Creates timer, the time it will last and the time between intervals
    fun timer(millisInFuture:Long, countDownInterval:Long):CountDownTimer {
        return object: CountDownTimer(millisInFuture, countDownInterval){
            override fun onTick(millisInFuture: Long) {

                var millisInFuture:Long = millisInFuture

                if (isPaused) {
                    textView_timer.text = "Paused"
                    button_start_pause.text = "Resume"
                    button_reset.isEnabled = true
                    resumeFromMillis = millisInFuture
                    cancel()
                } else {
                    //Convert the remaining time into minutes:seconds
                    val remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(millisInFuture)
                    millisInFuture -= TimeUnit.MINUTES.toMillis(remainingMinutes)
                    val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(millisInFuture)

                    textView_timer.text = String.format("%01d:%02d", remainingMinutes, remainingSeconds)
                    //textView_timer.text = (remainingMinutes).toString() + ":" + (remainingSeconds).toString()
                }
            }

            //Once the timer finishes
            override fun onFinish() {
                textView_timer.text = "Finished"
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

                resumeFromMillis = 0

                waveHelper.cancel()

                confettiSelector.firstTimeConfetti(viewKonfetti)
            }
        }
    }

    fun startOrPause(view: View) {
        if (!timerRunning && !isPaused) {
            startTimer(view)
        } else if (!isPaused) {
            pauseTimer(view)
        } else {
            resumeTimer(view)
        }
    }

    fun startTimer(view: View) {
        timer(millisInFuture, countDownInterval).start()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        timerRunning = true
        isPaused = false
        button_start_pause.text = "Pause"

        waveHelper.start()
    }

    fun pauseTimer(view: View) {
        isPaused = true
        timerRunning = false
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        waveHelper.cancel()
    }

    fun resumeTimer(view: View) {
        timer(resumeFromMillis, countDownInterval).start()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        timerRunning = true
        isPaused = false
        button_start_pause.text = "Pause"
        button_reset.isEnabled = false

        waveHelper.start()
    }

    fun resetTimer(view: View) {
        timerRunning = false
        isPaused = false
        textView_timer.text = ""
        resumeFromMillis = 0
        button_start_pause.text = "Start"
        button_reset.isEnabled = false
    }

    fun showTimePickerDialog(view: View) {
        var timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, "timePicker")
    }
}