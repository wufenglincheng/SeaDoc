package com.coder.seadoc.utils

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

/**
 * Created by liuting on 17/6/28.
 */
public fun dpToPx(dp:Int,context: Context):Int = (context.resources.displayMetrics.density * dp).toInt()

fun Activity.requestScreenWidth():Int{
    return this.resources.displayMetrics.widthPixels
}
fun FragmentActivity.requestScreenWidth():Int{
    return this.resources.displayMetrics.widthPixels
}
fun Fragment.requestScreenWidth():Int{
    return this.resources.displayMetrics.widthPixels
}