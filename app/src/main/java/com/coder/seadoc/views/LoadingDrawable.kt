package com.coder.seadoc.views

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.animation.DecelerateInterpolator
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by liuting on 17/7/31.
 */
class LoadingDrawable constructor(val size: Int) : Drawable() {
    private val mPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        strokeWidth = size * 0.1f
        isAntiAlias = true
    }

    private var mCurrentSize = 0f
    private var mCurrentAngel = 0f
    private var mAnim: ValueAnimator? = null
    private var mRequestStopAnim = AtomicBoolean(false)
    override fun draw(canvas: Canvas?) {
        canvas?.apply {
            mPaint.style = Paint.Style.FILL
            drawCircle(size * .5f, size * .5f, size * .5f + mCurrentSize, mPaint)
            mPaint.style = Paint.Style.STROKE
            val mRectF = RectF(-size * .4f - mCurrentSize, -size * .4f - mCurrentSize, size * 1.4f + mCurrentSize, size * 1.4f + mCurrentSize)
            drawArc(mRectF, 225f + mCurrentAngel, 90f, false, mPaint)
            drawArc(mRectF, 45f + mCurrentAngel, 90f, false, mPaint)
        }
    }

    fun start() {
        if (mAnim?.isRunning ?: false) mAnim?.cancel()
        mAnim = ObjectAnimator.ofFloat(0f, 1f).apply {
            duration = 1000
            repeatCount = -1
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                var value = it.animatedValue as Float
                mCurrentAngel = 360f * value
                if (value <= 0.5f) {
                    mCurrentSize = -size * .5f * value
                } else {
                    value = 1f - value
                    mCurrentSize = -size * .5f * value
                }
                invalidateSelf()
            }
            start()
        }
    }

    fun stop() {
        mAnim?.repeatCount = 1
    }


    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun getIntrinsicHeight(): Int {
        return size
    }

    override fun getIntrinsicWidth(): Int {
        return size
    }

    fun isLoading(): Boolean {
        return mAnim?.isRunning ?: false
    }
}