package com.mindorks

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator

class RadialProgressBar : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        parseAttributes(
            context.obtainStyledAttributes(
                attrs,
                R.styleable.RadialProgressBar
            )
        );
    }

    private var mViewWidth = width
    private var mViewHeight = height


    //Common Things
    private var mRoundedCorners = true
    private val mMaxSweepAngle = 360f


    //Data of the Outer View
    private var mStartAngleOuterView = -90
    private var mOuterProgress = 30
    private var mSweepAngleOuterView = 0
    private var mAnimationDurationOuterView = 400
    private var mMaxProgressOuterView = 100
    private var mProgressColorOuterView = Color.parseColor("#f52e67")
    private var mPaintOuterView = Paint(Paint.ANTI_ALIAS_FLAG)

    //Data of the Center View
    private var mStartAngleCenterView = -90
    private var mSweepAngleCenterView = 0
    private var mInnerProgress = 30
    private var mAnimationDurationCenterView = 400
    private var mMaxProgressCenterView = 100
    private var mProgressColorCenterView = Color.parseColor("#c2ff07")
    private var mPaintCenterView = Paint(Paint.ANTI_ALIAS_FLAG)


    //Data of the Inner View
    private var mStartAngleInnerView = -90
    private var mSweepAngleInnerView = 0
    private var mAnimationDurationInnerView = 400
    private var mMaxProgressInnerView = 100
    private var mProgressColorInnerView = Color.parseColor("#0dffab")
    private var mPaintInnerView = Paint(Paint.ANTI_ALIAS_FLAG)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initMeasurments()
        drawOuterProgress(canvas)
        drawCenterCircle(canvas)
        drawInnerCircle(canvas)
    }


    private fun parseAttributes(a: TypedArray?) {
        mOuterProgress = a!!.getInteger(R.styleable.RadialProgressBar_outerProgress, mOuterProgress)
        mProgressColorOuterView = a.getColor(R.styleable.RadialProgressBar_outerProgressColor, mProgressColorOuterView)
        mInnerProgress = a.getInteger(R.styleable.RadialProgressBar_innerProgress, mInnerProgress)
        a.recycle()
        setOuterProgress(mOuterProgress)
        setOuterProgressColor(mProgressColorOuterView)
        setInnerProgress(mInnerProgress)
    }

    private fun drawInnerCircle(canvas: Canvas?) {
        val diameter = Math.min(mViewWidth, mViewHeight)
        val paddingView = (diameter / 16.0).toFloat()
        val stroke = (diameter / 8).toFloat()
        val addVal = (stroke * 2) + 4f
        val subVal = ((stroke * 2) + paddingView + 4f)
        val oval = RectF(paddingView + addVal, paddingView + addVal, diameter - subVal, diameter - subVal)
        mPaintInnerView.color = mProgressColorInnerView
        mPaintInnerView.strokeWidth = stroke
        mPaintInnerView.isAntiAlias = true
        mPaintInnerView.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        mPaintInnerView.style = Paint.Style.STROKE
        canvas!!.drawArc(
            oval, mStartAngleInnerView.toFloat(), mSweepAngleInnerView.toFloat(), false, mPaintInnerView
        )
    }

    private fun drawCenterCircle(canvas: Canvas?) {
        val diameter = Math.min(mViewWidth, mViewHeight)
        val paddingView = (diameter / 16.0).toFloat()
        val stroke = (diameter / 8).toFloat()
        val addVal = stroke + 2f
        val subVal = (stroke + paddingView + 2f)
        val oval = RectF(paddingView + addVal, paddingView + addVal, diameter - subVal, diameter - subVal)
        mPaintCenterView.color = mProgressColorCenterView
        mPaintCenterView.strokeWidth = stroke
        mPaintCenterView.isAntiAlias = true
        mPaintCenterView.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        mPaintCenterView.style = Paint.Style.STROKE
        canvas!!.drawArc(
            oval, mStartAngleCenterView.toFloat(), mSweepAngleCenterView.toFloat(), false, mPaintCenterView
        )

    }

    private fun drawOuterProgress(canvas: Canvas?) {
        val diameter = Math.min(mViewWidth, mViewHeight)
        val paddingView = (diameter / 16.0).toFloat()
        val stroke = (diameter / 8).toFloat()
        val oval = RectF(paddingView, paddingView, diameter - paddingView, diameter - paddingView)
        mPaintOuterView.color = mProgressColorOuterView
        mPaintOuterView.strokeWidth = stroke
        mPaintOuterView.isAntiAlias = true
        mPaintOuterView.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        mPaintOuterView.style = Paint.Style.STROKE
        canvas!!.drawArc(
            oval, mStartAngleOuterView.toFloat(), mSweepAngleOuterView.toFloat(), false, mPaintOuterView
        )

    }

    private fun initMeasurments() {
        mViewWidth = width
        mViewHeight = height
    }

    private fun calcSweepAngleFromOuterProgress(progress: Int): Float {
        return (mMaxSweepAngle / mMaxProgressOuterView * progress)
    }

    private fun calcSweepAngleFromInnerProgress(progress: Int): Float {
        return (mMaxSweepAngle / mMaxProgressInnerView * progress)
    }

    private fun calcSweepAngleFromCenterProgress(progress: Int): Float {
        return (mMaxSweepAngle / mMaxProgressCenterView * progress)
    }

    fun getOuterProgress(): Int {
        return this.mOuterProgress
    }

    fun setOuterProgress(progress: Int) {
        if (progress != 0) mOuterProgress = progress
        val animator =
            ValueAnimator.ofFloat(mSweepAngleOuterView.toFloat(), calcSweepAngleFromOuterProgress(mOuterProgress))
        animator.interpolator = DecelerateInterpolator()
        animator.duration = mAnimationDurationOuterView.toLong()
        animator.addUpdateListener { valueAnimator ->
            val value: Float = valueAnimator.animatedValue as Float
            mSweepAngleOuterView = (value.toInt())
            invalidate()
        }
        animator.start()

    }

    fun setOuterProgressColor(color: Int) {
        mProgressColorOuterView = color
        invalidate()
    }

    fun useRoundedCorners(roundedCorners: Boolean) {
        mRoundedCorners = roundedCorners
        invalidate()
    }

    fun setInnerProgress(progress: Int) {
        if (progress != 0) mInnerProgress = progress
        val animator = ValueAnimator.ofFloat(mSweepAngleInnerView.toFloat(), calcSweepAngleFromInnerProgress(mInnerProgress))
        animator.interpolator = DecelerateInterpolator()
        animator.duration = mAnimationDurationInnerView.toLong()
        animator.addUpdateListener { valueAnimator ->
            val value: Float = valueAnimator.animatedValue as Float
            mSweepAngleInnerView = (value.toInt())
            invalidate()

        }
        animator.start()
    }

    fun setInnerProgressColor(color: Int) {
        mProgressColorInnerView = color
        invalidate()
    }

    fun setCenterProgress(progress: Int) {
        val animator =
            ValueAnimator.ofFloat(mSweepAngleCenterView.toFloat(), calcSweepAngleFromCenterProgress(progress))
        animator.interpolator = DecelerateInterpolator()
        animator.duration = mAnimationDurationCenterView.toLong()
        animator.addUpdateListener { valueAnimator ->
            val value: Float = valueAnimator.animatedValue as Float
            mSweepAngleCenterView = (value.toInt())
            invalidate()

        }
        animator.start()
    }

    fun setCenterProgressColor(color: Int) {
        mProgressColorCenterView = color
        invalidate()

    }
}