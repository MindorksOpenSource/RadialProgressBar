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
import android.graphics.Shader
import android.graphics.LinearGradient
import android.util.Log
import com.mindorks.util.Styleable
import com.mindorks.util.gradient
import com.mindorks.util.setupView
import kotlin.collections.ArrayList


/**
 * @author Himanshu Singh
 */

@Suppress("MemberVisibilityCanBePrivate", "unused")
open class RadialProgressBar : View {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        parseAttributes(
                context.obtainStyledAttributes(
                        attrs,
                        Styleable.RadialProgressBar
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
    private var mCircleThickness = 1f
    private var mCirclePadding = 10f

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
    private var mOuterColor = ArrayList<Int>()

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
    private var mCenterColor = ArrayList<Int>()


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
    private var mInnerColor = ArrayList<Int>()

    /**
     * @onDraw draws all the Layout on Screen
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initMeasurements()
        when {
            hasTwoProgressView -> {
                drawOuterProgressView(canvas)
                drawCenterProgressView(canvas)
            }
            hasOneProgressView -> {
                drawOuterProgressView(canvas)
            }
            !hasOneProgressView && !hasTwoProgressView -> {
                drawInnerProgressView(canvas)
                drawCenterProgressView(canvas)
                drawOuterProgressView(canvas)
            }
        }
    }


    /**
     * @parseAttributes parses all XML Styleables and sets them to the functions
     */
    private fun parseAttributes(a: TypedArray) {
        mOuterProgress = a.getInteger(Styleable.RadialProgressBar_outerProgress, mOuterProgress)
        mProgressColorOuterView = a.getColor(Styleable.RadialProgressBar_outerProgressColor, mProgressColorOuterView)
        mInnerProgress = a.getInteger(Styleable.RadialProgressBar_innerProgress, mInnerProgress)
        mProgressColorInnerView = a.getColor(Styleable.RadialProgressBar_innerProgressColor, mProgressColorInnerView)
        mCenterProgress = a.getInteger(Styleable.RadialProgressBar_centerProgress, mCenterProgress)
        mProgressColorCenterView =
                a.getColor(Styleable.RadialProgressBar_centerProgressColor, mProgressColorCenterView)
        hasOneProgressView = a.getBoolean(Styleable.RadialProgressBar_hasOneProgressView, hasOneProgressView)
        hasTwoProgressView = a.getBoolean(Styleable.RadialProgressBar_hasTwoProgressView, hasTwoProgressView)
        mRoundedCorners = a.getBoolean(Styleable.RadialProgressBar_useRoundedCorner, mRoundedCorners)
        isAnimationOn = a.getBoolean(Styleable.RadialProgressBar_isAnimationOn, isAnimationOn)
        mStartAngleOuterView = a.getInteger(Styleable.RadialProgressBar_outerProgressStartAngle, mStartAngleOuterView)
        mStartAngleCenterView =
                a.getInteger(Styleable.RadialProgressBar_centerProgressStartAngle, mStartAngleCenterView)
        mStartAngleInnerView = a.getInteger(Styleable.RadialProgressBar_innerProgressStartAngle, mStartAngleInnerView)
        mMaxProgressOuterView = a.getInteger(Styleable.RadialProgressBar_outerMaxProgress, mMaxProgressOuterView)
        mMaxProgressInnerView = a.getInteger(Styleable.RadialProgressBar_innerMaxProgress, mMaxProgressInnerView)
        mMaxProgressCenterView = a.getInteger(Styleable.RadialProgressBar_centerMaxProgress, mMaxProgressCenterView)
        mElevation = a.getBoolean(Styleable.RadialProgressBar_hasElevation, mElevation)
        mEmptyProgressBar = a.getBoolean(Styleable.RadialProgressBar_hasEmptyProgressBar, mEmptyProgressBar)
        mEmptyProgressColorCenterView =
                a.getColor(Styleable.RadialProgressBar_centerEmptyProgressColor, mEmptyProgressColorCenterView)
        mEmptyProgressColorOuterView =
                a.getColor(Styleable.RadialProgressBar_outerEmptyProgressColor, mEmptyProgressColorOuterView)
        mEmptyProgressColorInnerView =
                a.getColor(Styleable.RadialProgressBar_innerEmptyProgressColor, mEmptyProgressColorInnerView)
        mCircleThickness = a.getFloat(Styleable.RadialProgressBar_circleThickness, mCircleThickness)
        mCirclePadding = a.getFloat(Styleable.RadialProgressBar_circlePadding, mCirclePadding)
        a.recycle()
        hasElevation(mElevation)
        hasOneProgressView(hasOneProgressView)
        hasTwoProgressView(hasTwoProgressView)
        hasEmptyProgressBar(mEmptyProgressBar)
        setEmptyProgressColorCenterView(mEmptyProgressColorCenterView)
        setEmptyProgressColorOuterView(mEmptyProgressColorOuterView)
        setEmptyProgressColorInnerView(mEmptyProgressColorInnerView)
        setAnimationInProgressView(isAnimationOn)
        setMaxProgressOuterView(mMaxProgressOuterView)
        setMaxProgressInnerView(mMaxProgressInnerView)
        setMaxProgressCenterView(mMaxProgressCenterView)

        setOuterProgress(mOuterProgress)
        mOuterColor.add(mProgressColorOuterView)
        setOuterProgressColor(mOuterColor)

        setInnerProgress(mInnerProgress)
        mInnerColor.add(mProgressColorInnerView)
        setInnerProgressColor(mInnerColor)

        setCenterProgress(mCenterProgress)
        mCenterColor.add(mProgressColorCenterView)
        setCenterProgressColor(mCenterColor)

        useRoundedCorners(mRoundedCorners)
        setStartAngleCenterView(mStartAngleCenterView)
        setStartAngleInnerView(mStartAngleInnerView)
        setStartAngleOuterView(mStartAngleOuterView)
        setCircleThickness(mCircleThickness)
        setCirclePadding(mCirclePadding)

    }

    /**
     * Draws the Inner Progress View
     */
    private fun drawInnerProgressView(canvas: Canvas?) {
        val diameter = Math.min(mViewWidth, mViewHeight)
        val paddingView = (diameter / 16.0).toFloat()
        val stroke = (diameter / 8).toFloat() * mCircleThickness
        val addVal = (stroke * 2) + 2 * mCirclePadding
        val subVal = ((stroke * 2) + paddingView + 2 * mCirclePadding)
        val oval = RectF(paddingView + addVal, paddingView + addVal, diameter - subVal, diameter - subVal)
        mPaintInnerView.setupView(stroke, mRoundedCorners)

        if (mElevation) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, mPaintInnerView)
            mPaintInnerView.setShadowLayer(
                    1f, 1f, 0f, Color.argb(128, 0, 0, 0)
            )
        }
        if (mEmptyProgressBar) drawProgressBackArc(mEmptyProgressColorInnerView, oval, stroke, canvas)

        when (mInnerColor.size) {
            0 -> mPaintInnerView.color = mProgressColorInnerView
            1 -> mPaintInnerView.color = mInnerColor[0]
            2 -> mPaintInnerView.shader = gradient(mInnerColor[0], mInnerColor[1])
            else -> {
                mPaintInnerView.shader = gradient(mInnerColor[0], mInnerColor[1])
                Log.e("RadialProgressBar", "Inner Progress Color Can't Be more then Two colors")
            }
        }
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
        val stroke = (diameter / 8).toFloat() * mCircleThickness
        val addVal = stroke + mCirclePadding
        val subVal = (stroke + paddingView + mCirclePadding)
        val oval = RectF(paddingView + addVal, paddingView + addVal, diameter - subVal, diameter - subVal)
        mPaintCenterView.setupView(stroke, mRoundedCorners)

        if (mElevation) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, mPaintCenterView)
            mPaintCenterView.setShadowLayer(
                    1f, 1f, 0f, Color.argb(128, 0, 0, 0)
            )
        }
        if (mEmptyProgressBar) drawProgressBackArc(mEmptyProgressColorCenterView, oval, stroke, canvas)

        when (mCenterColor.size) {
            0 -> mPaintCenterView.color = mProgressColorInnerView
            1 -> mPaintCenterView.color = mCenterColor[0]
            2 -> mPaintCenterView.shader = gradient(mCenterColor[0], mCenterColor[1])
            else -> {
                mPaintCenterView.shader = gradient(mCenterColor[0], mCenterColor[1])
                Log.e("RadialProgressBar", "Inner Progress Color Can't Be more then Two colors")
            }
        }
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
        val stroke = (diameter / 8).toFloat() * mCircleThickness
        val oval = RectF(paddingView, paddingView, diameter - paddingView, diameter - paddingView)
        mPaintOuterView.setupView(stroke, mRoundedCorners)
        if (mElevation) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, mPaintOuterView)
            mPaintOuterView.setShadowLayer(
                    1f, 1f, 0f, Color.argb(128, 0, 0, 0)
            )
        }
        if (mEmptyProgressBar) drawProgressBackArc(mEmptyProgressColorOuterView, oval, stroke, canvas)

        when (mOuterColor.size) {
            0 -> mPaintOuterView.color = mProgressColorOuterView
            1 -> mPaintOuterView.color = mCenterColor[0]
            2 -> mPaintOuterView.shader = gradient(mOuterColor[0], mOuterColor[1])
            else -> {
                mPaintOuterView.shader = gradient(mOuterColor[0], mOuterColor[1])
                Log.e("RadialProgressBar", "Outer Progress Color Can't Be more then Two colors")
            }
        }
        canvas?.drawArc(oval, mStartAngleOuterView.toFloat(), mSweepAngleOuterView.toFloat(), false, mPaintOuterView)

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
        return when {
            !(hasOneProgressView && hasTwoProgressView) -> (mMaxSweepAngle / mMaxProgressInnerView * progress)
            else -> 0F
        }
    }

    /**
     * @return Sweep Angle for Center ProgressView
     * */
    private fun calcSweepAngleFromCenterProgress(progress: Int): Float {
        return when {
            !(hasOneProgressView && !hasTwoProgressView) -> (mMaxSweepAngle / mMaxProgressCenterView * progress)
            else -> 0F
        }
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
        return when {
            !(hasOneProgressView && hasTwoProgressView) -> this.mInnerProgress
            else -> 0
        }
    }

    /*
      * @return center progress value
      * */
    fun getCenterProgress(): Int {
        return when {
            !(hasOneProgressView && !hasTwoProgressView) -> this.mCenterProgress
            else -> 0
        }

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
        return when {
            !(hasOneProgressView && hasTwoProgressView) -> this.mStartAngleInnerView
            else -> 0
        }
    }

    /**
     * @return start angle center progressview
     * */
    fun getStartAngleCenterView(): Int {
        return when {
            !(hasOneProgressView && !hasTwoProgressView) -> this.mStartAngleCenterView
            else -> 0
        }
    }

    /**
    @return max progress value of inner progress view
     */
    fun getMaxProgressInnerView(): Int {
        return when {
            !(hasOneProgressView && hasTwoProgressView) -> this.mMaxProgressInnerView
            else -> 0
        }
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
        return when {
            !(hasOneProgressView && !hasTwoProgressView) -> this.mMaxProgressCenterView
            else -> 0
        }
    }

    /**
    @return thickness for the progress views
     */
    fun getCircleThickness(): Float {
        return mCircleThickness
    }

    /**
    @return padding between the progress views
     */
    fun getCirclePadding(): Float {
        return mCirclePadding
    }

    /**
    Set the Start Angle for Center Progress View
     */
    fun setStartAngleCenterView(angle: Int) {
        if (!(hasOneProgressView && !hasTwoProgressView)) {
            mStartAngleCenterView = angle
            invalidate()
        }
    }

    /**
    Set the  Color for Empty Center Progress View
     */
    fun setEmptyProgressColorCenterView(value: Int) {
        if (!(hasOneProgressView && !hasTwoProgressView)) {
            mEmptyProgressColorCenterView = value
            invalidate()
        }
    }

    /**
    Set the  Color for Empty Inner Progress View
     */
    fun setEmptyProgressColorInnerView(value: Int) {
        if (!(hasOneProgressView && hasTwoProgressView)) {
            mEmptyProgressColorInnerView = value
            invalidate()
        }
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
        if (!(hasOneProgressView && hasTwoProgressView)) {
            mStartAngleInnerView = angle
            invalidate()
        }
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
        if (!(hasOneProgressView && hasTwoProgressView)) {
            mMaxProgressInnerView = max
            invalidate()
        }
    }

    /**
    Set the  Maximum Progress of Center Progress View
     */
    fun setMaxProgressCenterView(max: Int) {
        if (!(hasOneProgressView && !hasTwoProgressView)) {
            mMaxProgressCenterView = max
            invalidate()
        }
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
    fun setOuterProgressColor(color: ArrayList<Int>) {
        mOuterColor = color
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
    fun setAnimationInProgressView(animationOn: Boolean) {
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
        when {
            hasTwoProgressView -> {
                setCenterProgress(centerProgress)
                setOuterProgress(outerProgress)
            }
            hasOneProgressView -> {
                setOuterProgress(outerProgress)
            }
            !hasOneProgressView && !hasTwoProgressView -> {
                setInnerProgress(innerProgress)
                setCenterProgress(centerProgress)
                setOuterProgress(outerProgress)
            }
        }
        invalidate()
    }

    /**
    set the max progress  for all progressview
     */
    fun setMaxProgressValues(innerProgress: Int, centerProgress: Int, outerProgress: Int) {
        when {
            hasTwoProgressView -> {
                setMaxProgressCenterView(centerProgress)
                setMaxProgressOuterView(outerProgress)
            }
            hasOneProgressView -> {
                setMaxProgressOuterView(outerProgress)
            }
            !hasOneProgressView && !hasTwoProgressView -> {
                setMaxProgressInnerView(innerProgress)
                setMaxProgressCenterView(centerProgress)
                setMaxProgressOuterView(outerProgress)
            }
        }
        invalidate()
    }

    /**
    set the progress  for inner progressview
     */
    fun setInnerProgress(progress: Int) {
        if (!(hasOneProgressView && hasTwoProgressView)) {
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
    }

    /**
    set the progress color for center progressview
     */
    fun setInnerProgressColor(color: ArrayList<Int>) {
        if (!(hasOneProgressView && hasTwoProgressView)) {
            mInnerColor = color
            invalidate()
        }
    }

    /**
    set the progress for center progressview
     */
    fun setCenterProgress(progress: Int) {
        if (!(hasOneProgressView && !hasTwoProgressView)) {
            if (progress != 0) mCenterProgress = progress
            val animator =
                    ValueAnimator.ofFloat(
                            mSweepAngleCenterView.toFloat(),
                            calcSweepAngleFromCenterProgress(mCenterProgress)
                    )
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
    }

    /**
    set the color for center progressview
     */
    fun setCenterProgressColor(color: ArrayList<Int>) {
        if (!(hasOneProgressView && !hasTwoProgressView)) {
            mCenterColor = color
            invalidate()
        }
    }

    /**
    set the condition to draw only outer progressview
     */
    fun hasOneProgressView(value: Boolean) {
        hasOneProgressView = value
        invalidate()
    }

    /**
    set the condition to draw only outer and inner progressview
     */
    fun hasTwoProgressView(value: Boolean) {
        hasTwoProgressView = value
        invalidate()
    }

    /**
    set the thickness for the progress views
    value should be between 0f to 1f
     */
    fun setCircleThickness(value: Float) {
        mCircleThickness = when {
            value < 0f -> 0f
            value > 1f -> 1f
            else -> value
        }
        invalidate()
    }

    /**
    set the padding between the progress views
     */
    fun setCirclePadding(value: Float) {
        mCirclePadding = value
        invalidate()
    }

}