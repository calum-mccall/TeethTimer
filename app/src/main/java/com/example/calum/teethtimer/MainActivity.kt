package com.example.calum.teethtimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val millisInFuture:Long = 120000
    val countDownInterval:Long = 1000

    var resumeFromMillis:Long = 0

    var confettiSelector = ConfettiTypes()

    var notifications = Notifications(this)

    var timerRunning = false
    var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    }

    fun pauseTimer(view: View) {
        isPaused = true
        timerRunning = false
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

    }

    fun resumeTimer(view: View) {
        timer(resumeFromMillis, countDownInterval).start()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        timerRunning = true
        isPaused = false
        button_start_pause.text = "Pause"
        button_reset.isEnabled = false

    }

    fun resetTimer(view: View) {
        timerRunning = false
        isPaused = false
        textView_timer.text = ""
        resumeFromMillis = 0
        button_start_pause.text = "Start"
        button_reset.isEnabled = false
    }

    fun setMorningAlarm(view: View) {
        var TAG = "MainActivity"

        var morningTimePickerFragment = TimePickerFragment()
        morningTimePickerFragment.show(supportFragmentManager, getString(R.string.morning_time_picker))
        Log.i(TAG, "Showing timePicker for morning alarm")
    }

    fun setEveningAlarm(view: View) {
        var TAG = "MainActivity"

        var eveningTimePickerFragment = TimePickerFragment()
        eveningTimePickerFragment.show(supportFragmentManager, getString(R.string.evening_time_picker))
        Log.i(TAG, "Showing timePicker for evening alarm")
    }

    fun showTimePickerDialog(view: View) {
        var timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, "timePicker")
    }
}