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

/**
 * @author Himanshu Singh
 */

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


    /**
    mViewWidth is the Width
    mViewHeight is the Height
     */
    private var mViewWidth = width
    private var mViewHeight = height


    /**
     * Common vars
     */
    private var isAnimationOn = true
    private var mRoundedCorners = true
    private val mMaxSweepAngle = 360f
    private var mElevation = false
    private var mEmptyProgressBar = false
    private var backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var hasOneProgressView = false
    private var hasTwoProgressView = false

    /**
     * Data of the Outer View
     */

    private var mStartAngleOuterView = 270
    private var mOuterProgress = 0
    private var mSweepAngleOuterView = 0
    private var mAnimationDurationOuterView = 400
    private var mMaxProgressOuterView = 100
    private var mProgressColorOuterView = Color.parseColor("#f52e67")
    private var mEmptyProgressColorOuterView = Color.parseColor("#F5F5F5")
    private var mPaintOuterView = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * Data of the Center View
     */
    private var mStartAngleCenterView = 270
    private var mSweepAngleCenterView = 0
    private var mAnimationDurationCenterView = 400
    private var mMaxProgressCenterView = 100
    private var mCenterProgress = 0
    private var mProgressColorCenterView = Color.parseColor("#c2ff07")
    private var mPaintCenterView = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mEmptyProgressColorCenterView = Color.parseColor("#F5F5F5")


    /**
     * Data of the Inner View
     */
    private var mStartAngleInnerView = 270
    private var mSweepAngleInnerView = 0
    private var mInnerProgress = 0
    private var mAnimationDurationInnerView = 400
    private var mMaxProgressInnerView = 100
    private var mProgressColorInnerView = Color.parseColor("#0dffab")
    private var mPaintInnerView = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mEmptyProgressColorInnerView = Color.parseColor("#F5F5F5")

    /**
     * @onDraw draws all the Layout on Screen
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initMeasurements()
        if (hasOneProgressView) {
            drawOuterProgressView(canvas)
        }
        if (hasTwoProgressView && !hasOneProgressView) {
            drawOuterProgressView(canvas)
            drawCenterProgressView(canvas)
        } else if (hasTwoProgressView && hasOneProgressView) {
            drawCenterProgressView(canvas)
        }
        if (!hasOneProgressView && !hasTwoProgressView) {
            drawInnerProgressView(canvas)
            drawCenterProgressView(canvas)
            drawOuterProgressView(canvas)
        }
    }


    /**
     * @parseAttributes parses all XML Styleables and sets them to the functions
     */
    private fun parseAttributes(a: TypedArray) {
        mOuterProgress = a.getInteger(R.styleable.RadialProgressBar_outerProgress, mOuterProgress)
        mProgressColorOuterView = a.getColor(R.styleable.RadialProgressBar_outerProgressColor, mProgressColorOuterView)
        mInnerProgress = a.getInteger(R.styleable.RadialProgressBar_innerProgress, mInnerProgress)
        mProgressColorInnerView = a.getColor(R.styleable.RadialProgressBar_innerProgressColor, mProgressColorInnerView)
        mCenterProgress = a.getInteger(R.styleable.RadialProgressBar_centerProgress, mCenterProgress)
        mProgressColorCenterView =
                a.getColor(R.styleable.RadialProgressBar_centerProgressColor, mProgressColorCenterView)
        hasOneProgressView = a.getBoolean(R.styleable.RadialProgressBar_hasOneProgressView, hasOneProgressView)
        hasTwoProgressView = a.getBoolean(R.styleable.RadialProgressBar_hasTwoProgressView, hasTwoProgressView)
        mRoundedCorners = a.getBoolean(R.styleable.RadialProgressBar_useRoundedCorner, mRoundedCorners)
        isAnimationOn = a.getBoolean(R.styleable.RadialProgressBar_isAnimationOn, isAnimationOn)
        mStartAngleOuterView = a.getInteger(R.styleable.RadialProgressBar_outerProgressStartAngle, mStartAngleOuterView)
        mStartAngleCenterView =
                a.getInteger(R.styleable.RadialProgressBar_centerProgressStartAngle, mStartAngleCenterView)
        mStartAngleInnerView = a.getInteger(R.styleable.RadialProgressBar_innerProgressStartAngle, mStartAngleInnerView)
        mMaxProgressOuterView = a.getInteger(R.styleable.RadialProgressBar_outerMaxProgress, mMaxProgressOuterView)
        mMaxProgressInnerView = a.getInteger(R.styleable.RadialProgressBar_innerMaxProgress, mMaxProgressInnerView)
        mMaxProgressCenterView = a.getInteger(R.styleable.RadialProgressBar_centerMaxProgress, mMaxProgressCenterView)
        mElevation = a.getBoolean(R.styleable.RadialProgressBar_hasElevation, mElevation)
        mEmptyProgressBar = a.getBoolean(R.styleable.RadialProgressBar_hasEmptyProgressBar, mEmptyProgressBar)
        mEmptyProgressColorCenterView =
                a.getColor(R.styleable.RadialProgressBar_centerEmptyProgressColor, mEmptyProgressColorCenterView)
        mEmptyProgressColorOuterView =
                a.getColor(R.styleable.RadialProgressBar_outerEmptyProgressColor, mEmptyProgressColorOuterView)
        mEmptyProgressColorInnerView =
                a.getColor(R.styleable.RadialProgressBar_innerEmptyProgressColor, mEmptyProgressColorInnerView)
        a.recycle()
        hasElevation(mElevation)
        hasEmptyProgressBar(mEmptyProgressBar)
        setEmptyProgressColorCenterView(mEmptyProgressColorCenterView)
        setEmptyProgressColorOuterView(mEmptyProgressColorOuterView)
        setEmptyProgressColorInnerView(mEmptyProgressColorInnerView)
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
        setHasOneProgressView(hasOneProgressView)
        setHasTwoProgressView(hasTwoProgressView)
    }

    /**
     * Draws the Inner Progress View
     */
    private fun drawInnerProgressView(canvas: Canvas?) {
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
        if (mElevation) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, mPaintInnerView)
            mPaintInnerView.setShadowLayer(
                1f, 1f, 0f, Color.argb(128, 0, 0, 0)
            )
        }
        if (mEmptyProgressBar) drawProgressBackArc(mEmptyProgressColorInnerView, oval, stroke, canvas)

        canvas?.drawArc(
            oval, mStartAngleInnerView.toFloat(), mSweepAngleInnerView.toFloat(), false, mPaintInnerView
        )

    }

    /**
     * Draws the Center ProgressView
     */
    private fun drawCenterProgressView(canvas: Canvas?) {
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
        if (mElevation) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, mPaintCenterView)
            mPaintCenterView.setShadowLayer(
                1f, 1f, 0f, Color.argb(128, 0, 0, 0)
            )
        }
        if (mEmptyProgressBar) drawProgressBackArc(mEmptyProgressColorCenterView, oval, stroke, canvas)
        canvas?.drawArc(
            oval, mStartAngleCenterView.toFloat(), mSweepAngleCenterView.toFloat(), false, mPaintCenterView
        )

    }

    /**
     * Draws the Outer ProgressView
     */
    private fun drawOuterProgressView(canvas: Canvas?) {
        val diameter = Math.min(mViewWidth, mViewHeight)
        val paddingView = (diameter / 16.0).toFloat()
        val stroke = (diameter / 8).toFloat()
        val oval = RectF(paddingView, paddingView, diameter - paddingView, diameter - paddingView)
        mPaintOuterView.color = mProgressColorOuterView
        mPaintOuterView.strokeWidth = stroke
        mPaintOuterView.isAntiAlias = true
        mPaintOuterView.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        mPaintOuterView.style = Paint.Style.STROKE
        if (mElevation) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, mPaintOuterView)
            mPaintOuterView.setShadowLayer(
                1f, 1f, 0f, Color.argb(128, 0, 0, 0)
            )
        }
        if (mEmptyProgressBar) drawProgressBackArc(mEmptyProgressColorOuterView, oval, stroke, canvas)
        canvas?.drawArc(
            oval, mStartAngleOuterView.toFloat(), mSweepAngleOuterView.toFloat(), false, mPaintOuterView
        )

    }

    /**
     * Draws the Empty Arc behing the ProgressView
     */
    private fun drawProgressBackArc(
        color: Int,
        oval: RectF,
        stroke: Float,
        canvas: Canvas?
    ) {
        backgroundPaint.isAntiAlias = true
        backgroundPaint.strokeWidth = stroke
        backgroundPaint.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        backgroundPaint.style = Paint.Style.STROKE
        backgroundPaint.color = color
        canvas?.drawArc(oval, 0.toFloat(), mMaxSweepAngle, false, backgroundPaint)
    }

    /**
     * Setup the Measurement
     */
    private fun initMeasurements() {
        mViewWidth = width
        mViewHeight = height
    }

    /**
     * @return Sweep Angle for Outer ProgressView
     * */
    private fun calcSweepAngleFromOuterProgress(progress: Int): Float {
        return (mMaxSweepAngle / mMaxProgressOuterView * progress)
    }

    /**
     * @return Sweep Angle for Inner ProgressView
     * */
    private fun calcSweepAngleFromInnerProgress(progress: Int): Float {
        return (mMaxSweepAngle / mMaxProgressInnerView * progress)
    }

    /**
     * @return Sweep Angle for Center ProgressView
     * */
    private fun calcSweepAngleFromCenterProgress(progress: Int): Float {
        return (mMaxSweepAngle / mMaxProgressCenterView * progress)
    }

    /**
     * @return outer progress value
     * */
    fun getOuterProgress(): Int {
        return this.mOuterProgress
    }

    /**
     * @return inner progress value
     * */
    fun getInnerProgress(): Int {
        return this.mInnerProgress
    }

    /*
      * @return center progress value
      * */
    fun getCenterProgress(): Int {
        return this.mCenterProgress
    }

    /**
     * @return start angle Outer progressview
     * */
    fun getStartAngleOuterView(): Int {
        return this.mStartAngleOuterView
    }

    /**
     * @return start angle Inner progressview
     * */
    fun getStartAngleInnerView(): Int {
        return this.mStartAngleInnerView
    }

    /**
     * @return start angle center progressview
     * */
    fun getStartAngleCenterView(): Int {
        return this.mStartAngleCenterView
    }

    /**
    @return max progress value of inner progress view
     */
    fun getMaxProgressInnerView(): Int {
        return this.mMaxProgressInnerView
    }

    /**
    @return max progress value of outer progress view
     */
    fun getMaxProgressOuterView(): Int {
        return this.mMaxProgressOuterView
    }

    /**
    @return max progress value of center progress view
     */
    fun getMaxProgressCenterView(): Int {
        return this.mMaxProgressCenterView
    }

    /**
    Set the Start Angle for Center Progress View
     */
    fun setStartAngleCenterView(angle: Int) {
        mStartAngleCenterView = angle
        invalidate()
    }

    /**
    Set the  Color for Empty Center Progress View
     */
    fun setEmptyProgressColorCenterView(value: Int) {
        mEmptyProgressColorCenterView = value
        invalidate()
    }

    /**
    Set the  Color for Empty Inner Progress View
     */
    fun setEmptyProgressColorInnerView(value: Int) {
        mEmptyProgressColorInnerView = value
        invalidate()
    }

    /**
    Set the  Color for Empty Outer Progress View
     */
    fun setEmptyProgressColorOuterView(value: Int) {
        mEmptyProgressColorOuterView = value
        invalidate()
    }

    /**
    Set the Start Angle for Outer Progress View
     */
    fun setStartAngleOuterView(angle: Int) {
        mStartAngleOuterView = angle
        invalidate()
    }

    /**
    Set the Start Angle for Inner Progress View
     */
    fun setStartAngleInnerView(angle: Int) {
        mStartAngleInnerView = angle
        invalidate()
    }

    /**
    Set the  Maximum Progress of Outer Progress View
     */
    fun setMaxProgressOuterView(max: Int) {
        mMaxProgressOuterView = max
        invalidate()
    }

    /**
    Set the  Maximum Progress of Inner Progress View
     */
    fun setMaxProgressInnerView(max: Int) {
        mMaxProgressInnerView = max
        invalidate()
    }

    /**
    Set the  Maximum Progress of Center Progress View
     */
    fun setMaxProgressCenterView(max: Int) {
        mMaxProgressCenterView = max
        invalidate()
    }

    /**
    Set the   Progress of Outer Progress View
     */
    fun setOuterProgress(progress: Int) {
        if (progress != 0) mOuterProgress = progress
        val animator =
            ValueAnimator.ofFloat(mSweepAngleOuterView.toFloat(), calcSweepAngleFromOuterProgress(mOuterProgress))
        animator.removeAllUpdateListeners()
        animator.interpolator = DecelerateInterpolator()
        animator.duration = mAnimationDurationOuterView.toLong()
        animator.addUpdateListener { valueAnimator ->
            val value: Float = valueAnimator.animatedValue as Float
            mSweepAngleOuterView = (value.toInt())
            invalidate()
        }
        animator.start()

    }

    /**
     * Set Color for Outer ProgressView
     */
    fun setOuterProgressColor(color: Int) {
        mProgressColorOuterView = color
        invalidate()
    }

    /**
     * Set the Elevations
     */
    fun hasElevation(value: Boolean) {
        mElevation = value
        invalidate()
    }

    /**
     * Set the Condition for Empty Progress Views in BG
     */
    fun hasEmptyProgressBar(value: Boolean) {
        mEmptyProgressBar = value
        invalidate()
    }


    /**
    set the condition for setting the animation for progressview
     */
    private fun setAnimationInProgressView(animationOn: Boolean) {
        if (!animationOn) {
            mAnimationDurationOuterView = 0
            mAnimationDurationInnerView = 0
            mAnimationDurationCenterView = 0
        }
    }

    /**
    set the condition for setting the value for Rounded Corners
     */
    fun useRoundedCorners(roundedCorners: Boolean) {
        mRoundedCorners = roundedCorners
        invalidate()
    }

    /**
    set the  progress for all progressview
     */
    fun setProgressValues(innerProgress: Int, centerProgress: Int, outerProgress: Int) {
        setInnerProgress(innerProgress)
        setCenterProgress(centerProgress)
        setOuterProgress(outerProgress)
        invalidate()

    }

    /**
    set the max progress  for all progressview
     */
    fun setMaxProgressValues(innerProgress: Int, centerProgress: Int, outerProgress: Int) {
        setMaxProgressInnerView(innerProgress)
        setMaxProgressCenterView(centerProgress)
        setMaxProgressOuterView(outerProgress)
        invalidate()
    }

    /**
    set the progress  for inner progressview
     */
    fun setInnerProgress(progress: Int) {
        if (progress != 0) mInnerProgress = progress
        val animator =
            ValueAnimator.ofFloat(mSweepAngleInnerView.toFloat(), calcSweepAngleFromInnerProgress(mInnerProgress))
        animator.removeAllUpdateListeners()
        animator.interpolator = DecelerateInterpolator()
        animator.duration = mAnimationDurationInnerView.toLong()
        animator.addUpdateListener { valueAnimator ->
            val value: Float = valueAnimator.animatedValue as Float
            mSweepAngleInnerView = (value.toInt())
            invalidate()

        }
        animator.start()
    }

    /**
    set the progress color for center progressview
     */
    fun setInnerProgressColor(color: Int) {
        mProgressColorInnerView = color
        invalidate()
    }

    /**
    set the progress for center progressview
     */
    fun setCenterProgress(progress: Int) {
        if (progress != 0) mCenterProgress = progress
        val animator =
            ValueAnimator.ofFloat(mSweepAngleCenterView.toFloat(), calcSweepAngleFromCenterProgress(mCenterProgress))
        animator.removeAllUpdateListeners()
        animator.interpolator = DecelerateInterpolator()
        animator.duration = mAnimationDurationCenterView.toLong()
        animator.addUpdateListener { valueAnimator ->
            val value: Float = valueAnimator.animatedValue as Float
            mSweepAngleCenterView = (value.toInt())
            invalidate()

        }
        animator.start()
    }

    /**
    set the color for center progressview
     */
    fun setCenterProgressColor(color: Int) {
        mProgressColorCenterView = color
        invalidate()

    }

    /**
    set the condition to draw only outer progressview
     */
    private fun setHasOneProgressView(value: Boolean) {
        hasOneProgressView = value
        invalidate()
    }

    /**
    set the condition to draw only outer and inner progressview
     */
    private fun setHasTwoProgressView(value: Boolean) {
        hasTwoProgressView = value
        invalidate()
    }

}