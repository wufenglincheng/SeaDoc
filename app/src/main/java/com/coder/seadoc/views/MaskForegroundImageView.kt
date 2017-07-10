package com.coder.seadoc.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.annotation.FloatRange
import android.util.AttributeSet

class MaskForegroundImageView(context: Context, attrs: AttributeSet) : ForegroundImageView(context, attrs) {
    private var mask: Drawable? = null
    private var lastAlpha: Int = 0


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mask != null) {
            mask!!.setBounds(0, 0, w, h)
        }
    }

    override fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        if (mask != null) mask!!.jumpToCurrentState()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        if (mask != null && mask!!.isStateful) {
            mask!!.state = drawableState
        }
    }

    fun setMask(drawable: Drawable) {
        if (mask !== drawable) {
            if (mask != null) {
                mask!!.callback = null
                unscheduleDrawable(mask)
            }

            mask = drawable

            if (mask != null) {
                mask!!.setBounds(0, 0, width, height)
                setWillNotDraw(false)
                mask!!.callback = this
                if (mask!!.isStateful) {
                    mask!!.state = drawableState
                }
            } else {
                setWillNotDraw(true)
            }
            invalidate()
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (null != mask) {
            mask!!.draw(canvas)
        }
    }

    /**
     * 动态修改前置drawable透明度

     * @param alpha
     */
    fun setForegroundAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
        if (null == foreground) {
            return
        }
        //为了提升性能，如果alpha变化超过0.05 才生效
        if ((alpha * 20).toInt() != lastAlpha || lastAlpha == 0) {
            lastAlpha = (alpha * 20).toInt()
            foreground.alpha = (alpha * 255).toInt()
        }
    }
}