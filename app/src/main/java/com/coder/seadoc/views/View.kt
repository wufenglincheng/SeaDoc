package com.coder.seadoc.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Created by liuting on 17/6/30.
 */

class View : LinearLayout {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}
}
