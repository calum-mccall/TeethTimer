package com.example.calum.teethtimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.example.calum.teethtimer.R.string.timer_value
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

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
                val remainingTime = millisInFuture/1000

                textView_timer.text = (remainingTime).toString()
            }

            //Once the timer finishes
            override fun onFinish() {
                textView_timer.text = "Finished"
                button_start.isEnabled = true
            }
        }
    }

    //When user clicks start button
    fun startTimer(view: View) {
        timer(millisInFuture = (1000 * 30), countDownInterval = 1000).start()
        button_start.isEnabled = false
    }
}
