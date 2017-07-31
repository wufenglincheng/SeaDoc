package com.coder.seadoc.views

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View

/**
 * Created by liuting on 17/6/5.
 */
open internal class FloatBtnBehavior : CoordinatorLayout.Behavior<View> {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor() : super()

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        //依赖于appBarLayout
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        if (dependency == null) {
            return false
        }

        if (child is FloatingActionButton) {
            //percent 从1到0
            val percent = dependency.top / dependency.height.toFloat() + 1F
            child.alpha = percent
            child.translationY = (1-percent) * child.height
        }
        return true
    }
}
