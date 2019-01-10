package com.mindorks

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
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
        )
    }

    private var mViewWidth = width
    private var mViewHeight = height


    //Common Things
    private var isAnimationOn = true
    private var mRoundedCorners = true
    private val mMaxSweepAngle = 360f


    //Data of the Outer View
    private var mStartAngleOuterView = 270
    private var mOuterProgress = 0
    private var mSweepAngleOuterView = 0
    private var mAnimationDurationOuterView = 400
    private var mMaxProgressOuterView = 100
    private var mProgressColorOuterView = Color.parseColor("#f52e67")
    private var mPaintOuterView = Paint(Paint.ANTI_ALIAS_FLAG)

    //Data of the Center View
    private var mStartAngleCenterView = 270
    private var mSweepAngleCenterView = 0
    private var mAnimationDurationCenterView = 400
    private var mMaxProgressCenterView = 100
    private var mCenterProgress = 0
    private var mProgressColorCenterView = Color.parseColor("#c2ff07")
    private var mPaintCenterView = Paint(Paint.ANTI_ALIAS_FLAG)


    //Data of the Inner View
    private var mStartAngleInnerView = 270
    private var mSweepAngleInnerView = 0
    private var mInnerProgress = 0
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
        mProgressColorInnerView = a.getColor(R.styleable.RadialProgressBar_innerProgressColor, mProgressColorInnerView)
        mCenterProgress = a.getInteger(R.styleable.RadialProgressBar_centerProgress, mCenterProgress)
        mProgressColorCenterView =
                a.getColor(R.styleable.RadialProgressBar_centerProgressColor, mProgressColorCenterView)
        mRoundedCorners = a.getBoolean(R.styleable.RadialProgressBar_useRoundedCorner, mRoundedCorners)
        isAnimationOn = a.getBoolean(R.styleable.RadialProgressBar_isAnimationOn, isAnimationOn)
        mStartAngleOuterView = a.getInteger(R.styleable.RadialProgressBar_outerProgressStartAngle, mStartAngleOuterView)
        mStartAngleCenterView =
                a.getInteger(R.styleable.RadialProgressBar_centerProgressStartAngle, mStartAngleCenterView)
        mStartAngleInnerView = a.getInteger(R.styleable.RadialProgressBar_innerProgressStartAngle, mStartAngleInnerView)
        mMaxProgressOuterView = a.getInteger(R.styleable.RadialProgressBar_outerMaxProgress, mMaxProgressOuterView)
        mMaxProgressInnerView = a.getInteger(R.styleable.RadialProgressBar_innerMaxProgress, mMaxProgressInnerView)
        mMaxProgressCenterView = a.getInteger(R.styleable.RadialProgressBar_centerMaxProgress, mMaxProgressCenterView)
        a.recycle()
        setAnimationInProgressView(isAnimationOn)
        setMaxProgressOuterView(mMaxProgressOuterView)
        setMaxProgressInnerView(mMaxProgressInnerView)
        setMaxProgressCenterView(mMaxProgressCenterView)
        setOuterProgress(mOuterProgress)
        setOuterProgressColor(mProgressColorOuterView)
        setInnerProgress(mInnerProgress)
        setInnerProgressColor(mProgressColorInnerView)
        setCenterProgress(mCenterProgress)
        setCenterProgressColor(mProgressColorCenterView)
        useRoundedCorners(mRoundedCorners)
        setStartAngleCenterView(mStartAngleCenterView)
        setStartAngleInnerView(mStartAngleInnerView)
        setStartAngleOuterView(mStartAngleOuterView)
    }


    private fun drawInnerCircle(canvas: Canvas?) {
        val diameter = Math.min(mViewWidth, mViewHeight)
        val paddingView = (diameter / 16.0).toFloat()
        val stroke = (diameter / 8).toFloat()
        val addVal = (stroke * 2) + 8f
        val subVal = ((stroke * 2) + paddingView + 8f)
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
        val addVal = stroke + 4f
        val subVal = (stroke + paddingView + 4f)
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

    fun getInnerProgress(): Int {
        return this.mInnerProgress
    }

    fun getCenterProgress(): Int {
        return this.mCenterProgress
    }

    fun getStartAngleOuterView(): Int {
        return this.mStartAngleOuterView
    }

    fun getStartAngleInnerView(): Int {
        return this.mStartAngleInnerView
    }

    fun getStartAngleCenterView(): Int {
        return this.mStartAngleCenterView
    }

    fun getMaxProgressInnerView(): Int {
        return this.mMaxProgressInnerView
    }

    fun getMaxProgressOuterView(): Int {
        return this.mMaxProgressOuterView
    }

    fun getMaxProgressCenterView(): Int {
        return this.mMaxProgressCenterView
    }

    fun setStartAngleCenterView(angle: Int) {
        mStartAngleCenterView = angle
        invalidate()
    }

    fun setStartAngleOuterView(angle: Int) {
        mStartAngleOuterView = angle
        invalidate()
    }

    fun setStartAngleInnerView(angle: Int) {
        mStartAngleInnerView = angle
        invalidate()
    }

    fun setMaxProgressOuterView(max: Int) {
        mMaxProgressOuterView = max
        invalidate()
    }

    fun setMaxProgressInnerView(max: Int) {
        mMaxProgressInnerView = max
        invalidate()
    }

    fun setMaxProgressCenterView(max: Int) {
        mMaxProgressCenterView = max
        invalidate()
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

    private fun setAnimationInProgressView(animationOn: Boolean) {
        if (!animationOn) {
            mAnimationDurationOuterView = 0
            mAnimationDurationInnerView = 0
            mAnimationDurationCenterView = 0
        }
    }

    fun useRoundedCorners(roundedCorners: Boolean) {
        mRoundedCorners = roundedCorners
        invalidate()
    }

    fun setProgressValues(innerProgress: Int, centerProgress: Int, outerProgress: Int) {
        setInnerProgress(innerProgress)
        setCenterProgress(centerProgress)
        setOuterProgress(outerProgress)
        invalidate()

    }
    fun setMaxProgressValues(innerProgress: Int, centerProgress: Int, outerProgress: Int) {
        setMaxProgressInnerView(innerProgress)
        setMaxProgressCenterView(centerProgress)
        setMaxProgressOuterView(outerProgress)
        invalidate()
    }

    fun setInnerProgress(progress: Int) {
        if (progress != 0) mInnerProgress = progress
        val animator =
            ValueAnimator.ofFloat(mSweepAngleInnerView.toFloat(), calcSweepAngleFromInnerProgress(mInnerProgress))
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
        if (progress != 0) mCenterProgress = progress
        val animator =
            ValueAnimator.ofFloat(mSweepAngleCenterView.toFloat(), calcSweepAngleFromCenterProgress(mCenterProgress))
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