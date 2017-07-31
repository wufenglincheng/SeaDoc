package com.coder.seadoc.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.coder.seadoc.R

/**
 * Created by liuting on 17/7/28.
 */
class CircleBgView : View {
    private var mPaint: Paint = Paint()
    private var mStartColor = 0xFFFFFFFF.toInt()
    private var mEndColor = 0xFFFFFFFF.toInt()
    private var startRadius = 0f
    private var startX = 0f
    private var startY = 0f
    private var endRadius = 0f
    private var currentRadius = 0f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        mStartColor = context.resources.getColor(R.color.colorAccent)
        mPaint.color = mStartColor
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(startX, startY, currentRadius, mPaint)
    }

    fun setView(view: View) {
        startRadius = view.width * 0.5f
        currentRadius = startRadius
        var location = IntArray(2)
        view.getLocationInWindow(location)
        startX = location[0] + startRadius
        startY = view.top + startRadius
        endRadius = Math.sqrt((startY * startY + startX * startY).toDouble()).toFloat()
        postInvalidate()
    }

    fun setExpanded(isExpanded: Boolean) {
        if (isExpanded) {
            startExpandedAnim()
        } else {
            endExpandedAnim()
        }
    }

    private fun startExpandedAnim() {
        var bgAnim = getBgAnim(startRadius, endRadius)
        var argbAnim = getArgbAnim(mStartColor, mEndColor)
        AnimatorSet().apply {
            playTogether(bgAnim, argbAnim)
            duration = 300
            start()
        }
    }

    private fun endExpandedAnim() {
        var bgAnim = getBgAnim(endRadius, startRadius)
        var argbAnim = getArgbAnim(mEndColor, mStartColor)
        AnimatorSet().apply {
            playTogether(bgAnim, argbAnim)
            duration = 300
            start()
        }
    }

    private fun getArgbAnim(mStartColor: Int, mEndColor: Int): ValueAnimator {
        return ObjectAnimator.ofObject(ArgbEvaluator(), mStartColor, mEndColor).apply {
            duration = 300
            addUpdateListener {
                mPaint.color = it.animatedValue as Int
            }
        }
    }

    private fun getBgAnim(startRadius: Float, endRadius: Float): ValueAnimator {
        return ObjectAnimator.ofFloat(startRadius, endRadius).apply {
            duration = 300
            addUpdateListener {
                currentRadius = it.animatedValue as Float
                postInvalidate()
            }
        }
    }

}