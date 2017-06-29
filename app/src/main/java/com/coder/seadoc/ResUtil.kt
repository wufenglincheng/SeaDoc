package com.coder.seadoc

import android.content.Context
import android.graphics.drawable.Drawable

/**
 * Created by liuting on 17/6/27.
 */
class ResUtil private constructor(val context: Context) {

    fun getDrawable(id: Int): Drawable {
        return context.resources.getDrawable(id)
    }

    companion object {
        var instance: ResUtil? = null
        fun inject(context: Context) {
            if (instance == null) {
                instance = ResUtil(context)
            }
        }
    }
}