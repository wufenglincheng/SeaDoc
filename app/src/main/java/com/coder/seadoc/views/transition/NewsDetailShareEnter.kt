package com.coder.seadoc.views.transition

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.support.annotation.RequiresApi
import android.transition.ChangeBounds
import android.transition.TransitionValues
import android.util.AttributeSet
import android.view.View

@RequiresApi(Build.VERSION_CODES.KITKAT)
/**
 * Created by liuting on 17/7/7.
 */
class NewsDetailShareEnter constructor(context: Context, attrs: AttributeSet):ChangeBounds(context,attrs) {
    private val PROPNAME_BOUNDS = "android:changeBounds:bounds"
    private val PROPNAME_PARENT = "android:changeBounds:parent"
    override fun captureEndValues(transitionValues: TransitionValues) {
        super.captureEndValues(transitionValues)
        val width = (transitionValues.values[PROPNAME_PARENT] as View).width
        val bounds = transitionValues.values[PROPNAME_BOUNDS] as Rect
        bounds.right = width
        bounds.bottom = width * 9 / 16
        transitionValues.values.put(PROPNAME_BOUNDS, bounds)
    }
}