package com.example.calum.teethtimer

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import me.itangqi.waveloadingview.WaveLoadingView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val millisInFuture:Long = 120000
    val countDownInterval:Long = 1000

    var resumeFromMillis:Long = 0

    var confettiSelector = ConfettiTypes()

    var notifications = Notifications(this)

    var timerRunning = false
    var isPaused = false

    val TAG = "Main Activity"

    var morningAlarm = ""
    var eveningAlarm = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = this.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)
        showSetAlarms(sharedPreferences)
    }

    //Creates timer, the time it will last and the time between intervals
    fun timer(millisInFuture:Long, countDownInterval:Long):CountDownTimer {
        return object: CountDownTimer(millisInFuture, countDownInterval){
            override fun onTick(millisInFuture: Long) {

                var millisInFuture:Long = millisInFuture

                if (millisInFuture < 10000 && millisInFuture > 9000) {
                    waveView.centerTitleSize += 4
                }

                if (isPaused) {
                    waveView.centerTitle = "Paused"
                    //waveLoadingView.centerTitle = "Pause"
                    //textView_timer.text = "Paused"
                    button_start_pause.text = "Resume"
                    button_reset.isEnabled = true
                    resumeFromMillis = millisInFuture
                    cancel()
                } else {
                    //Convert the remaining time into minutes:seconds
                    val remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(millisInFuture)
                    millisInFuture -= TimeUnit.MINUTES.toMillis(remainingMinutes)
                    val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(millisInFuture)

                    waveView.centerTitle = String.format("%01d:%02d", remainingMinutes, remainingSeconds)
                    waveView.progressValue += 1
                    Log.i(TAG, "Wave is at progress value " + waveView.progressValue)

                    if (remainingMinutes < 1 && remainingSeconds < 10) {
                        waveView.centerTitle = String.format("%01d", remainingSeconds)
                        //waveView.centerTitleSize += 1
                    }
                    //textView_timer.text = String.format("%01d:%02d", remainingMinutes, remainingSeconds)
                    //textView_timer.text = (remainingMinutes).toString() + ":" + (remainingSeconds).toString()
                }
            }

            //Once the timer finishes
            override fun onFinish() {
                waveView.centerTitle = "Finished"
                //waveLoadingView.centerTitle = "Finished"
                //textView_timer.text = "Finished"
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

                resumeFromMillis = 0

                confettiSelector.firstTimeConfetti(viewKonfetti)

                waveView.endAnimation()

                button_reset.isEnabled = true
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

        waveView.startAnimation()
        waveView.progressValue = -10
    }

    fun pauseTimer(view: View) {
        isPaused = true
        timerRunning = false
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        waveView.pauseAnimation()
    }

    fun resumeTimer(view: View) {
        timer(resumeFromMillis, countDownInterval).start()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        timerRunning = true
        isPaused = false
        button_start_pause.text = "Pause"
        button_reset.isEnabled = false

        waveView.resumeAnimation()
    }

    fun resetTimer(view: View) {
        waveView.centerTitle = ""
        waveView.progressValue = 50
        waveView.startAnimation()
        timerRunning = false
        isPaused = false
        //waveLoadingView.centerTitle = ""
        //textView_timer.text = ""
        resumeFromMillis = 0
        button_start_pause.text = "Start"
        button_reset.isEnabled = false
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
}