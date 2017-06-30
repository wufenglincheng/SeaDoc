package com.coder.seadoc.utils

import android.content.Context

/**
 * Created by liuting on 17/6/28.
 */
public fun dpToPx(dp:Int,context: Context):Int = (context.resources.displayMetrics.density * dp).toInt()