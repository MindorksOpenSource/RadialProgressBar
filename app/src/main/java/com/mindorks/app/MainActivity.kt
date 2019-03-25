package com.mindorks.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.TextView
import com.mindorks.RadialProgressBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         progress.setProgressValues(70, 90, 90)
            txtvStatusCircle.text= String.format("Outer : %d",progress.getOuterProgress())




    }
}

