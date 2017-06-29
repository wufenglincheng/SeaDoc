package com.coder.seadoc

import android.content.Context

/**
 * Created by liuting on 17/6/28.
 */
fun dpToPx(dp:Int,context: Context):Int = (context.resources.displayMetrics.density * dp).toInt()