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

    fun timer(millisInFuture:Long, countDownInterval:Long):CountDownTimer {
        return object: CountDownTimer(millisInFuture, countDownInterval){
            override fun onTick(millisInFuture: Long) {
                textView_timer.text = (millisInFuture/1000).toString()
            }

            override fun onFinish() {
                textView_timer.text = "Finished"
            }
        }
    }

    fun startTimer(view: View) {
        timer(millisInFuture = (1000 * 60), countDownInterval = 1000).start()
    }
}
