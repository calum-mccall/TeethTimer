package io.github.calumcmccall.teethtimer

import android.graphics.Color
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
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