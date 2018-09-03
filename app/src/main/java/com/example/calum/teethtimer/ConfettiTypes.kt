package com.example.calum.teethtimer

import android.graphics.Color
import android.view.View
import com.example.calum.teethtimer.R.id.viewKonfetti
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import kotlinx.android.synthetic.main.activity_main.*
import nl.dionsegijn.konfetti.KonfettiView

class ConfettiTypes {

    fun firstTimeConfetti(viewKonfettiView: KonfettiView) {

        viewKonfettiView.build()
                .addColors(Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.GREEN)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.CIRCLE, Shape.RECT)
                .addSizes(Size(12))
                .setPosition(-50f, viewKonfettiView.width + 50f, -50f, -50f)
                .streamFor(300, 2500L);
    }

}