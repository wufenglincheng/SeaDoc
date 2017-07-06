package com.coder.seadoc.utils

import android.util.SparseArray
import android.view.View

/**
 * Created by liuting on 17/7/6.
 */
class ViewHolder constructor(val itemView: View) {
    val views = SparseArray<View>()
    fun <T : View> get(id: Int): T {
        var view = views[id] as? T
        if (view == null) {
            view = itemView.findViewById(id) as T
        }
        return view
    }
}