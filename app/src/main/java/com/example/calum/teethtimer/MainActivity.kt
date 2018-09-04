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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //Creates timer, the time it will last and the time between intervals
    fun timer(millisInFuture:Long, countDownInterval:Long):CountDownTimer {
        return object: CountDownTimer(millisInFuture, countDownInterval){
            //Callback on interval time
            override fun onTick(millisInFuture: Long) {

                //Display the remaining time as minutes:seconds
                //val remainingTime = millisInFuture/1000

                var millisInFuture:Long = millisInFuture

                //Convert the remaining time into minutes:seconds
                val remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(millisInFuture)
                millisInFuture -= TimeUnit.MINUTES.toMillis(remainingMinutes)
                val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(millisInFuture)

                textView_timer.text = String.format("%01d:%02d",remainingMinutes, remainingSeconds)
                //textView_timer.text = (remainingMinutes).toString() + ":" + (remainingSeconds).toString()

            }

            //Once the timer finishes
            override fun onFinish() {
                textView_timer.text = "Finished"
                button_start.isEnabled = true
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

                confettiSelector.firstTimeConfetti(viewKonfetti)

            }
        }
    }

    //When user clicks start button
    fun startTimer(view: View) {
        timer(millisInFuture = (1000 * 120), countDownInterval = 1000).start()
        button_start.isEnabled = false
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}