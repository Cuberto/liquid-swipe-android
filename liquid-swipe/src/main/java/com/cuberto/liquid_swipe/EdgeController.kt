package com.cuberto.liquid_swipe

import android.animation.ValueAnimator
import android.graphics.*
import android.view.MotionEvent
import androidx.core.animation.doOnEnd
import com.cuberto.liquid_swipe.animation.AnimationHelper
import com.cuberto.liquid_swipe.animation.Direction.LEFT
import com.cuberto.liquid_swipe.animation.Direction.NONE
import com.cuberto.liquid_swipe.animation.Direction.RIGHT

abstract class EdgeController(
    protected var width: Int = 0,
    protected var height: Int = 0,
    protected var waveCenterY: Float = 0f,
    protected var density: Float,
    protected val view: ViewI
) {

    protected val SWITCH_ANIMATION_DURATION = 700L
    protected val Y_RATIO = 0.7f

    protected var helper: AnimationHelper

    protected var progress: Float = 0f
    protected var translateMatrix = Matrix()
    protected val erasorPaint: Paint = Paint(Color.TRANSPARENT)
    protected val sourceOutPaint: Paint = Paint()

    protected var currentItem = 0
    protected var enabled = false
    protected var shouldDraw = true
    protected var switchingPage = false
    protected var bitmap: Bitmap? = null

    protected var animatingY: Boolean = false
    protected var currentY: Float = 0f
    protected var currentX: Float = 0f

    protected var yAnimator: ValueAnimator? = null

    protected var swipeDirection = NONE


    init {
        erasorPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        erasorPaint.style = Paint.Style.FILL
        erasorPaint.flags = Paint.ANTI_ALIAS_FLAG
        sourceOutPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
        helper = AnimationHelper(
            width.toFloat(),
            height.toFloat(),
            density
        )
        waveCenterY = Y_RATIO * height
    }

    fun hasBitmap(): Boolean {
        return bitmap != null
    }

    open fun onPageChanged(newPosition: Int) {
        translateMatrix.setTranslate(width.toFloat() * newPosition, 0f)
        currentX = width.toFloat()
        currentY = Y_RATIO * height
    }

    abstract fun onDownTouch(ev: MotionEvent): Boolean

    abstract fun onUpTouch(ev: MotionEvent): Boolean

    abstract fun onMoveTouch(ev: MotionEvent): Boolean

    abstract fun drawEdge(canvas: Canvas?)

    protected abstract fun updateProgress(rawX: Float)

    protected fun animateY(y: Float, duration: Long = 150) {
        animatingY = true
        yAnimator?.cancel()
        yAnimator = ValueAnimator.ofFloat(currentY, y)
        yAnimator?.duration = duration
        yAnimator?.addUpdateListener {
            currentY = it.animatedValue as Float
            waveCenterY = currentY
            updateProgress(currentX)
            view.redraw()
        }
        yAnimator?.doOnEnd { animatingY = false }
        yAnimator?.start()
    }

    protected fun animate(
        currentX: Float,
        endX: Float,
        duration: Long = SWITCH_ANIMATION_DURATION
    ): ValueAnimator {
        val animator = ValueAnimator.ofFloat(currentX, endX)
        var count = 0
        animator.addUpdateListener {
            updateProgress(it.animatedValue as Float)
            view.redraw()
            count++
        }
        animator.duration = duration
        return animator
    }

    protected fun animateRight(currentX: Float, switchPage: Boolean, duration: Long = 150) {
        if (switchPage) {
            view.blockInput(true)
        }
        val animator = animate(currentX, width.toFloat(), duration)
        animator.doOnEnd {
            if (swipeDirection == RIGHT) {
                if (currentItem > 0) {
                    if (switchPage) {
                        view.blockInput(false)
                        updateProgress(0f)
                        switchingPage = false
                        view.switchPage(RIGHT)
                        view.redraw()
                    }
                }
            }
        }
        if (switchPage) {
            animateY(Y_RATIO * height, duration)
        }
        animator.start()
    }

    protected fun animateLeft(currentX: Float, switchPage: Boolean) {
        if (switchPage) {
            view.blockInput(true)
        }
        val animator = animate(currentX, (width - (width * (1 / 0.45f))))
        animator.doOnEnd {
            if (swipeDirection == LEFT) {
                if (currentItem < view.getCount() + 1) {
                    if (switchPage) {
                        view.blockInput(false)
                        switchingPage = false
                        view.switchPage(LEFT)
                        view.redraw()
                        onPageSwitchedLeft()
                    }
                }
            }
        }
        animator.start()
    }

    protected open fun onPageSwitchedLeft() {}

}