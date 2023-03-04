package com.cuberto.liquid_swipe.animation

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import androidx.core.animation.doOnEnd
import com.cuberto.liquid_swipe.EdgeController
import com.cuberto.liquid_swipe.ViewI
import com.cuberto.liquid_swipe.animation.Direction.LEFT
import com.cuberto.liquid_swipe.animation.Direction.RIGHT
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class RightEdgeController(
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
        swipeDirection = LEFT
        enabled = true
        bitmap = null
    }

    private val touchOffset: Float = 0.1f
    private val DEFAULT_BUTTON_WIDTH = 48

    private val scaleMatrix = Matrix()
    private var animatingScale = false
    private var scaleAnimator: ValueAnimator? = null
    private var button: Drawable? = null
    private var buttonLeft = 0
    private var buttonTop = 0
    private var buttonRight = 0
    private var buttonBottom = 0
    private var buttonWidth = 48
    private var buttonAlpha = 1f
    private var listeningForButtonClick = false

    fun setButtonDrawable(button: Drawable?) {
        this.button = button
    }

    override fun onPageChanged(newPosition: Int) {
        super.onPageChanged(newPosition)
        currentItem = newPosition
        shouldDraw = true
        if (currentItem >= view.getCount() - 1) {
            enabled = false
            bitmap = null
        } else {
            enabled = true
            bitmap = view.getBitmap(RIGHT)
        }
        updateProgress(width.toFloat())
        view.redraw()
    }

    override fun onDownTouch(ev: MotionEvent): Boolean {
        if (isAButtonPress(ev)) {
            listeningForButtonClick = true
            return true
        }
        currentX = ev.x
        if (ev.x > width - width * touchOffset) {
            shouldDraw = true
            if (currentItem == view.getCount() - 1) {
                return false
            }
            swipeDirection = LEFT
            if (abs(ev.y - currentY) < height / 10) {
                currentY = ev.y
                animatingY = false
            } else {
                animateY(ev.y)
            }
            if (!animatingY) {
                currentY = ev.y
                waveCenterY = currentY
            }
            return true
        } else if (enabled && currentItem > 0 && ev.x < width * touchOffset / 2) {
            animateScale(1f, 0f)
            shouldDraw = false
            return false
        } else {
            shouldDraw = false
            return false
        }
    }

    override fun onUpTouch(ev: MotionEvent): Boolean {
        if (listeningForButtonClick) {
            if (isAButtonPress(ev)) {
                animateLeft(ev.x, true)
                listeningForButtonClick = false
                return true
            }
            listeningForButtonClick = false
        }
        if (shouldDraw) {
            if (!switchingPage) {
                currentY = ev.y
                animateY(height * Y_RATIO)
            }
            if (1 - ev.x / width < 0.4) {
                switchingPage = false
                animateRight(ev.x, false)
            } else {
                switchingPage = true
                animateLeft(ev.x, true)
            }
        } else {
            if (1 - ev.x / width > 0.7) {
                if (animatingScale) {
                    scaleAnimator?.reverse()
                    scaleAnimator?.doOnEnd {
                        shouldDraw = true
                    }
                } else {
                    animateScale(0f, 1f)
                    scaleAnimator?.doOnEnd {
                        shouldDraw = true
                    }
                }
            }
        }
        return true
    }

    override fun onMoveTouch(ev: MotionEvent): Boolean {
        listeningForButtonClick = false
        currentX = ev.x
        currentY = ev.y
        return if (shouldDraw) {
            currentX = ev.x
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

    override fun updateProgress(rawX: Float) {
        val x = width - rawX
        val maxChange = width * (1.0 / 0.45)
        progress = min(1.0, max(0.0, x / maxChange)).toFloat()
    }

    override fun drawEdge(canvas: Canvas?) {
        if (!enabled || bitmap == null) {
            return
        }
        if (shouldDraw || animatingScale) {
            drawPath(canvas)
            canvas?.drawBitmap(bitmap!!, currentItem * width.toFloat(), 0f, sourceOutPaint)
        }

    }

    private fun drawPath(canvas: Canvas?) {
        val wl = WaveLayer(
            waveCenterY,
            helper.waveHorRadius(progress),
            helper.waveVertRadius(progress),
            helper.sideWidth(progress),
            LEFT
        )
        wl.updatePath(width.toFloat(), height.toFloat())
        translateMatrix.setTranslate(width.toFloat() * currentItem, 0f)
        if (animatingScale) {
            wl.path.transform(scaleMatrix)
        }
        wl.path.transform(translateMatrix)
        canvas?.drawPath(wl.path, erasorPaint)
        drawButton(canvas)
    }

    private fun drawButton(canvas: Canvas?) {
        if (canvas == null || button == null) {
            return
        }
        updateButtonBounds()
        if (!animatingScale) {
            buttonAlpha = helper.btnAlpha(progress)
        }
        button?.alpha = (buttonAlpha * 255).toInt()
        button?.setBounds(buttonLeft, buttonTop, buttonRight, buttonBottom)
        button?.draw(canvas)
    }

    private fun updateButtonBounds() {
        buttonLeft =
            (width - (helper.waveHorRadius(progress) - helper.sideWidth(progress) + 24 * density) + width * currentItem).toInt()
        buttonTop = (waveCenterY - ((buttonWidth / 2) * density)).toInt()
        buttonBottom = (waveCenterY + ((buttonWidth / 2) * density)).toInt()
        buttonRight = (buttonLeft + buttonWidth * density).toInt()
    }

    private fun isAButtonPress(ev: MotionEvent): Boolean {
        return ev.x >= buttonLeft - width * currentItem && ev.x <= buttonRight - width * currentItem && ev.y >= buttonTop && ev.y <= buttonBottom
    }

    override fun onPageSwitchedLeft() {
        animateScale(0f, 1f)
    }

    private fun animateScale(from: Float, to: Float, duration: Long = 300) {
        scaleAnimator = ValueAnimator.ofFloat(from, to)
        scaleAnimator?.duration = duration
        scaleAnimator?.addUpdateListener {
            animatingScale = true
            val v = it.animatedValue as Float
            buttonAlpha = v * v * v
            scaleMatrix.setScale(v, 1f, width.toFloat(), 0f)
            view.redraw()
        }
        scaleAnimator?.doOnEnd {
            if (to == 1f) {
                shouldDraw = true
            }
            updateProgress(width.toFloat())
            animatingScale = false
        }
        scaleAnimator?.start()
        animatingScale = true
    }
}