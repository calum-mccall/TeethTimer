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

        val millisInFuture:Long = 1000 * 60
        val countDownInterval:Long = 1000
    }

    fun timer(millisInFuture:Long, countDownInterval:Long):CountDownTimer {
        return object: CountDownTimer(millisInFuture, countDownInterval){
            override fun onTick(p0: Long) {
                textView_timer.text = "$millisInFuture"
            }

            override fun onFinish() {
                textView_timer.text = "Finished"
            }
        }
    }

    fun startTimer(view: View) {
        textView_timer.text = "2:00"

        timer(millisInFuture = (1000), countDownInterval = 1000).start()
    }
}
