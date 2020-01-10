package com.cuberto.liquidswipetest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cuberto.liquid_swipe.LiquidPager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pager = findViewById<LiquidPager>(R.id.pager)
        pager.adapter = Adapter(supportFragmentManager)
    }
}
