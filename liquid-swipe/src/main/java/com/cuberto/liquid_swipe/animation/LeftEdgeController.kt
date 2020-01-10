package com.cuberto.liquid_swipe.animation

import android.graphics.Canvas
import android.view.MotionEvent
import com.cuberto.liquid_swipe.EdgeController
import com.cuberto.liquid_swipe.ViewI
import com.cuberto.liquid_swipe.animation.Direction.LEFT
import com.cuberto.liquid_swipe.animation.Direction.RIGHT
import kotlin.math.max
import kotlin.math.min

class LeftEdgeController(
    viewWidth: Int,
    viewHeight: Int,
    waveCenterY: Float,
    density: Float,
    view: ViewI
) :
    EdgeController(
        viewWidth,
        viewHeight,
        waveCenterY,
        density,
        view
    ) {

    init {
        swipeDirection = RIGHT
        enabled = false
        bitmap = null
    }

    private val touchOffset: Float = 0.05f

    override fun onPageChanged(newPosition: Int) {
        super.onPageChanged(newPosition)
        currentItem = newPosition
        if (currentItem == 0) {
            enabled = false
            bitmap = null
        } else {
            enabled = true
            bitmap = view.getBitmap(LEFT)
        }
        shouldDraw = false
        updateProgress(0f)
    }

    override fun onDownTouch(ev: MotionEvent): Boolean {
        currentX = ev.x
        currentY = ev.y
        if (enabled && ev.x < width * touchOffset) {
            shouldDraw = true
            if (currentItem == 0) {
                return false
            }
            waveCenterY = ev.y
            return true
        } else {
            shouldDraw = false
            return false
        }
    }

    override fun onUpTouch(ev: MotionEvent): Boolean {
        if (shouldDraw) {
            if (1 - ev.x / width > 0.7) {
                switchingPage = false
                animateLeft(ev.x, false)
            } else {
                switchingPage = true
                animateRight(ev.x, true, 700)
            }
        }
        return true
    }

    override fun onMoveTouch(ev: MotionEvent): Boolean {
        currentX = ev.x
        currentY = ev.y
        return if (shouldDraw) {
            if (!animatingY) {
                waveCenterY = ev.y
                updateProgress(ev.x)
                view.redraw()
            }
            true
        } else {
            false
        }
    }

    override fun drawEdge(canvas: Canvas?) {
        if (!enabled || bitmap == null) {
            return
        }
        if(shouldDraw) {
            drawPath(canvas)
            canvas?.drawBitmap(bitmap!!, view.getCurrentItem() * width.toFloat(), 0f, sourceOutPaint)
        }
    }

    override fun updateProgress(rawX: Float) {
        val x = width - rawX
        val maxChange = width.toDouble()
        this.progress = min(1.0, max(0.0, x / maxChange)).toFloat()
    }

    private fun drawPath(canvas: Canvas?) {
        val wl = WaveLayer(
            waveCenterY,
            helper.waveHorRadiusBack(progress),
            helper.waveVertRadius(progress),
            helper.sideWidth(progress),
            RIGHT
        )
        wl.updatePath(width.toFloat(), height.toFloat())
        translateMatrix.setTranslate(width.toFloat() * view.getCurrentItem(), 0f)
        wl.path.transform(translateMatrix)
        canvas?.drawPath(wl.path, erasorPaint)
    }

}