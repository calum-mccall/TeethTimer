package com.example.calum.teethtimer

import android.content.Context
import android.content.Intent
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

    val TAG = "Main Activity"

    var morningAlarm = ""
    var eveningAlarm = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = this.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)
        //showSetAlarms(sharedPreferences)
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
                    button_start.text = "Resume"
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

                button_start.isEnabled = false
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
        //button_start_pause.text = "Pause"

        button_start.visibility = View.INVISIBLE
        button_alarm_fragment.visibility = View.INVISIBLE
        button_pause_resume.visibility = View.VISIBLE

        waveView.startAnimation()
        waveView.progressValue = -10

        button_pause_resume.setImageResource(R.drawable.ic_pause_black_24dp)
    }

    fun pauseTimer(view: View) {
        isPaused = true
        timerRunning = false
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        waveView.pauseAnimation()

        button_reset.visibility = View.VISIBLE

        button_pause_resume.setImageResource(R.drawable.ic_play_arrow_black_24dp)
    }

    fun resumeTimer(view: View) {
        timer(resumeFromMillis, countDownInterval).start()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        timerRunning = true
        isPaused = false
        button_start.text = "Pause"
        button_reset.isEnabled = false

        waveView.resumeAnimation()

        button_reset.visibility = View.INVISIBLE

        button_pause_resume.setImageResource(R.drawable.ic_pause_black_24dp)
    }

    fun resetTimer(view: View) {
        waveView.centerTitleSize = "24".toFloat()
        waveView.centerTitle = ""
        waveView.progressValue = 50
        waveView.startAnimation()
        timerRunning = false
        isPaused = false
        //waveLoadingView.centerTitle = ""
        //textView_timer.text = ""
        resumeFromMillis = 0
        button_start.text = "Start"
        button_start.isEnabled = true
        button_reset.isEnabled = false

        button_start.visibility = View.VISIBLE
        button_alarm_fragment.visibility = View.VISIBLE
        button_pause_resume.visibility = View.INVISIBLE
        button_reset.visibility = View.INVISIBLE
    }

    /*fun showSetAlarms(sharedPreferences: SharedPreferences) {
        textView_morning_alarm.text = sharedPreferences.getString(R.string.morning_alarm_time.toString(), morningAlarm)
        textView_evening_alarm.text = sharedPreferences.getString(R.string.evening_alarm_time.toString(), eveningAlarm)
    }*/

    fun showAlarms(view: View) {
        val intent = Intent(this, AlarmsActivity::class.java)
        startActivity(intent)
    }
}