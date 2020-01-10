package com.cuberto.liquid_swipe.animation

import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.pow

class AnimationHelper(var viewWidth: Float, var viewHeight: Float, var density: Float) {

    private var initialHorRadius: Float = 48f * density
    private var maxHorRadius: Float = viewWidth * 0.8f

    private var initialVertRadius: Float = 82f * density
    private var maxVertRadius: Float = viewHeight * 0.9f
    private var initialSideWidth: Float = 15f * density

    fun btnAlpha(progress: Float): Float {
        val p1 = 0.1f
        val p2 = 0.2f
        if (progress <= p1) {
            return 1.0f
        }
        if (progress >= p2) {
            return 0.0f
        }
        return 1.0f - (progress - p1) / (p2 - p1)
    }

    fun waveHorRadius(progress: Float): Float {
        if (progress <= 0) {
            return initialHorRadius
        }
        if (progress >= 1) {
            return 0f
        }
        val p1 = 0.4f
        if (progress <= p1) {
            return initialHorRadius + progress / p1 * (maxHorRadius - initialHorRadius)
        }
        val t: Double = (progress - p1) / (1.0 - p1)
        val A: Double = maxHorRadius.toDouble()
        val r = 40.0
        val m = 9.8
        val beta: Double = r / (2 * m)
        val k = 50.0
        val omega0: Double = k / m
        val omega = (-beta.pow(2) + omega0.pow(2)).pow(0.5)

        return (A * exp(-beta * t) * cos(omega * t)).toFloat()
    }

    fun waveHorRadiusBack(progress: Float): Float {
        if (progress <= 0) {
            return initialHorRadius
        }
        if (progress >= 1) {
            return 0f
        }
        val p1 = 0.4f
        if (progress <= p1) {
            return initialHorRadius + progress / p1 * initialHorRadius
        }
        val t: Double = (progress - p1) / (1.0 - p1)
        val A: Double = 2.0 * initialHorRadius
        val r = 40.0
        val m = 9.8
        val beta: Double = (r / (2.0 * m))
        val k = 50.0
        val omega0: Double = (k / m)
        val omega = (-beta.pow(2.0) + omega0.pow(2.0)).pow(0.5)

        return (A * exp(-beta * t) * cos(omega * t)).toFloat()
    }

    fun waveVertRadius(progress: Float): Float {
        val p1 = 0.4f
        if (progress <= 0) {
            return initialVertRadius
        }
        if (progress >= p1) {
            return maxVertRadius
        }
        return initialVertRadius + (maxVertRadius - initialVertRadius) * progress / p1
    }

    fun sideWidth(progress: Float): Float {
        val p1 = 0.2f
        val p2 = 0.8f
        if (progress <= p1) {
            return initialSideWidth
        }
        if (progress >= p2) {
            return viewWidth
        }
        return initialSideWidth + (viewWidth - initialSideWidth) * (progress - p1) / (p2 - p1)
    }
}