package com.example.calum.teethtimer

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.example.calum.teethtimer.R.string.timer_value
import kotlinx.android.synthetic.main.activity_main.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import java.util.*
import java.util.concurrent.TimeUnit
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
                //val remainingTime = millisInFuture/1000

                var millisInFuture:Long = millisInFuture

                //Convert the remaining time into minutes:seconds
                val remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(millisInFuture)
                millisInFuture -= TimeUnit.MINUTES.toMillis(remainingMinutes)
                val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(millisInFuture)

                textView_timer.text = String.format("%01d:%02d",remainingMinutes, remainingSeconds)
                //textView_timer.text = (remainingMinutes).toString() + ":" + (remainingSeconds).toString()

                waveLoadingView.progressValue = waveLoadingView.progressValue - 10;
            }

            //Once the timer finishes
            override fun onFinish() {
                textView_timer.text = "Finished"
                button_start.isEnabled = true

                viewKonfetti.build()
                        .addColors(Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.GREEN)
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.CIRCLE, Shape.RECT)
                        .addSizes(Size(12))
                        .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
                        .streamFor(300, 2500L);

                waveLoadingView.cancelAnimation();
            }
        }
    }

    //When user clicks start button
    fun startTimer(view: View) {
        timer(millisInFuture = (1000 * 5), countDownInterval = 1000).start()
        button_start.isEnabled = false
    }
}
