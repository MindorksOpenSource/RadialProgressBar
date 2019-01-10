package com.mindorks.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progress.useRoundedCorners(false)
        progress.setMaxProgressValues(200,200,200)
        progress.setProgressValues(100,100,100)

    }
}
