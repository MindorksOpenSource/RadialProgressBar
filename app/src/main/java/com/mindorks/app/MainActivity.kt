package com.mindorks.app

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progress.setProgressValues(70, 90, 90)
        val outerColor = ArrayList<Int>()
        outerColor.add(Color.parseColor("#fbab00"))
        outerColor.add(Color.parseColor("#f5004b"))
        progress.setOuterProgressColor(outerColor)


        val innerColor = ArrayList<Int>()
        innerColor.add(Color.parseColor("#5eb3fc"))
        innerColor.add(Color.parseColor("#28007d"))
        progress.setInnerProgressColor(innerColor)

        val centerColor = ArrayList<Int>()
        centerColor.add(Color.parseColor("#3affaa"))
        centerColor.add(Color.parseColor("#1b93ff"))
        progress.setCenterProgressColor(centerColor)
    }
}
