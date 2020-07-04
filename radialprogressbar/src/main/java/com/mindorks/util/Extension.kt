package com.mindorks.util

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.view.View

fun View.gradient(colorFirst: Int, colorSecond: Int) = LinearGradient(
    0f, 0f, 0f,
    height.toFloat(),
    colorFirst,
    colorSecond,
    Shader.TileMode.MIRROR
)

fun Paint.setupView(stroke: Float, mRoundedCorners: Boolean) {
    strokeWidth = stroke
    isAntiAlias = true
    strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
    style = Paint.Style.STROKE
}