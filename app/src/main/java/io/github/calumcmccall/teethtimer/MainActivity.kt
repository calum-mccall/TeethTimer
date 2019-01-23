package io.github.calumcmccall.teethtimer

import android.content.Context
import android.content.Intent
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val millisInFuture:Long = 120000
    val countDownInterval:Long = 1000

    var resumeFromMillis:Long = 0

    var confettiSelector = ConfettiTypes()

    var timerRunning = false
    var isPaused = false

    val TAG = "Main Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.action_info -> {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)

            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
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
                    cancel()

                    waveView.centerTitle = "Paused"
                    resumeFromMillis = millisInFuture

                    window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

                    waveView.pauseAnimation()
                    button_reset.visibility = View.VISIBLE
                    button_pause_resume.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                } else {
                    //Convert the remaining time into minutes:seconds
                    val remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(millisInFuture)
                    millisInFuture -= TimeUnit.MINUTES.toMillis(remainingMinutes)
                    val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(millisInFuture)

                    waveView.centerTitle = String.format("%01d:%02d", remainingMinutes, remainingSeconds)
                    waveView.progressValue += 1

                    if (remainingMinutes < 1 && remainingSeconds < 10) {
                        waveView.centerTitle = String.format("%01d", remainingSeconds)
                    }

                    if (remainingSeconds <60 && remainingSeconds >58 || remainingSeconds <31 && remainingSeconds >29) {
                        vibrate()
                    }
                }
            }

            //Once the timer finishes
            override fun onFinish() {
                waveView.centerTitle = "Finished"
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

                resumeFromMillis = 0

                confettiSelector.firstTimeConfetti(viewKonfetti)

                waveView.endAnimation()

                button_start.isEnabled = false

                button_pause_resume.visibility = View.INVISIBLE
                button_reset.visibility = View.VISIBLE

                brushedProgress()
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
    }

    fun resumeTimer(view: View) {
        timer(resumeFromMillis, countDownInterval).start()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        timerRunning = true
        isPaused = false

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
        resumeFromMillis = 0
        button_start.isEnabled = true

        button_start.visibility = View.VISIBLE
        button_alarm_fragment.visibility = View.VISIBLE
        button_pause_resume.visibility = View.INVISIBLE
        button_reset.visibility = View.INVISIBLE
    }

    fun showAlarms(view: View) {
        val intent = Intent(this, AlarmsActivity::class.java)
        startActivity(intent)
    }

    fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(100)
        }
    }

    fun brushedProgress() {
        progressBar_day.incrementProgressBy(50)
    }
}