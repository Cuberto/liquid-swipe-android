package com.cuberto.liquid_swipe.animation

import android.graphics.Path
import com.cuberto.liquid_swipe.animation.Direction.RIGHT

internal class WaveLayer(
    var waveCenterY: Float,
    var waveHorRadius: Float,
    var waveVertRadius: Float,
    var sideWidth: Float,
    val swipeDirection : Int
) {
    var path = Path()

    fun updatePath(width: Float, height: Float) {
        path = Path()
        val maskWidth = width - sideWidth
        path.moveTo(maskWidth - sideWidth, 0f)
        if(swipeDirection == RIGHT) {
            path.lineTo(0f, 0f)
            path.lineTo(0f, height)
        }else{
            path.lineTo(width, 0f)
            path.lineTo(width, height)
        }
        path.lineTo(maskWidth, height)
        val curveStartY = waveCenterY + waveVertRadius

        path.lineTo(maskWidth, curveStartY)

        path.cubicTo(
            maskWidth,
            curveStartY - waveVertRadius * 0.1346194756f,
            maskWidth - waveHorRadius * 0.05341339583f,
            curveStartY - waveVertRadius * 0.2412779634f,
            maskWidth - waveHorRadius * 0.1561501458f,
            curveStartY - waveVertRadius * 0.3322374268f
        )
        path.cubicTo(
            maskWidth - waveHorRadius * 0.2361659167f,
            curveStartY - waveVertRadius * 0.4030805244f,
            maskWidth - waveHorRadius * 0.3305285625f,
            curveStartY - waveVertRadius * 0.4561193293f,
            maskWidth - waveHorRadius * 0.5012484792f,
            curveStartY - waveVertRadius * 0.5350576951f
        )
        path.cubicTo(
            maskWidth - waveHorRadius * 0.515878125f,
            curveStartY - waveVertRadius * 0.5418222317f,
            maskWidth - waveHorRadius * 0.5664134792f,
            curveStartY - waveVertRadius * 0.5650349878f,
            maskWidth - waveHorRadius * 0.574934875f,
            curveStartY - waveVertRadius * 0.5689655122f
        )
        path.cubicTo(
            maskWidth - waveHorRadius * 0.7283715208f,
            curveStartY - waveVertRadius * 0.6397387195f,
            maskWidth - waveHorRadius * 0.8086618958f,
            curveStartY - waveVertRadius * 0.6833456585f,
            maskWidth - waveHorRadius * 0.8774032292f,
            curveStartY - waveVertRadius * 0.7399037439f
        )
        path.cubicTo(
            maskWidth - waveHorRadius * 0.9653464583f,
            curveStartY - waveVertRadius * 0.8122605122f,
            maskWidth - waveHorRadius,
            curveStartY - waveVertRadius * 0.8936183659f,
            maskWidth - waveHorRadius,
            curveStartY - waveVertRadius
        )
        path.cubicTo(
            maskWidth - waveHorRadius,
            curveStartY - waveVertRadius * 1.100142878f,
            maskWidth - waveHorRadius * 0.9595746667f,
            curveStartY - waveVertRadius * 1.1887991951f,
            maskWidth - waveHorRadius * 0.8608411667f,
            curveStartY - waveVertRadius * 1.270484439f
        )
        path.cubicTo(
            maskWidth - waveHorRadius * 0.7852123333f,
            curveStartY - waveVertRadius * 1.3330544756f,
            maskWidth - waveHorRadius * 0.703382125f,
            curveStartY - waveVertRadius * 1.3795848049f,
            maskWidth - waveHorRadius * 0.5291125625f,
            curveStartY - waveVertRadius * 1.4665102805f
        )
        path.cubicTo(
            maskWidth - waveHorRadius * 0.5241858333f,
            curveStartY - waveVertRadius * 1.4689677195f,
            maskWidth - waveHorRadius * 0.505739125f,
            curveStartY - waveVertRadius * 1.4781625854f,
            maskWidth - waveHorRadius * 0.5015305417f,
            curveStartY - waveVertRadius * 1.4802616098f
        )
        path.cubicTo(
            maskWidth - waveHorRadius * 0.3187486042f,
            curveStartY - waveVertRadius * 1.5714239024f,
            maskWidth - waveHorRadius * 0.2332057083f,
            curveStartY - waveVertRadius * 1.6204116463f,
            maskWidth - waveHorRadius * 0.1541165417f,
            curveStartY - waveVertRadius * 1.687403f
        )
        path.cubicTo(
            maskWidth - waveHorRadius * 0.0509933125f,
            curveStartY - waveVertRadius * 1.774752061f,
            maskWidth,
            curveStartY - waveVertRadius * 1.8709256829f,
            maskWidth,
            curveStartY - waveVertRadius * 2
        )

        path.lineTo(maskWidth, 0f)
        path.lineTo(maskWidth - sideWidth, 0f)
        path.close()
    }
}