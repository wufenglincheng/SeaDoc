package com.coder.seadoc.views

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View

/**
 * Created by liuting on 17/6/28.
 */

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val views = SparseArray<View>()

    abstract fun bindData(data: T)

    protected fun bindView(id: Int): View? {
        var view: View? = views.get(id)
        if (view == null) {
            view = itemView.findViewById(id)
            views.put(id, view)
        }
        return view
    }
}
