package com.coder.seadoc.views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.coder.seadoc.R

/**
 * Created by liuting on 17/7/31.
 */
class FloatingLoadBar : View {
    private val SIZE_MINI = 1
    private val SIZE_NORMAL = 0

    private var mBackgroundColor = 0
    private var mSize = SIZE_NORMAL

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        mBackgroundColor = context.resources.getColor(R.color.colorAccent)

    }


    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
//            drawCircle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val preferredSize = getSizeDimension()

        val w = resolveAdjustedSize(preferredSize, widthMeasureSpec)
        val h = resolveAdjustedSize(preferredSize, heightMeasureSpec)

        // As we want to stay circular, we set both dimensions to be the
        // smallest resolved dimension
        val d = Math.min(w, h)

        // We add the shadow's padding to the measured dimension
        setMeasuredDimension(
                d ,
                d)
    }
    internal fun getSizeDimension(): Int {
        when (mSize) {
            SIZE_MINI -> return resources.getDimensionPixelSize(android.support.design.R.dimen.design_fab_size_mini)
            else -> return resources.getDimensionPixelSize(android.support.design.R.dimen.design_fab_size_normal)
        }
    }

    private fun resolveAdjustedSize(desiredSize: Int, measureSpec: Int): Int {
        var result = desiredSize
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)
        when (specMode) {
            View.MeasureSpec.UNSPECIFIED ->
                // Parent says we can be as big as we want. Just don't be larger
                // than max size imposed on ourselves.
                result = desiredSize
            View.MeasureSpec.AT_MOST ->
                // Parent says we can be as big as we want, up to specSize.
                // Don't be larger than specSize, and don't be larger than
                // the max size imposed on ourselves.
                result = Math.min(desiredSize, specSize)
            View.MeasureSpec.EXACTLY ->
                // No choice. Do what we are told.
                result = specSize
        }
        return result
    }

}