package com.cuberto.liquid_swipe

import android.graphics.Bitmap

interface ViewI {
    fun getBitmap(direction: Int) : Bitmap?
    fun getBitmapAt(position: Int) : Bitmap?
    fun getCount(): Int
    fun redraw()
    fun switchPage(direction: Int)
    fun getCurrentItem(): Int
    fun blockInput(block: Boolean)
}