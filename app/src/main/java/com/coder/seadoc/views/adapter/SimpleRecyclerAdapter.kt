package com.coder.seadoc.views.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.coder.seadoc.views.BaseViewHolder

/**
 * Created by liuting on 17/6/28.
 */
open abstract class SimpleRecyclerAdapter<T>(val context: Context) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    var mData: ArrayList<T> = ArrayList()

    fun setData(data: ArrayList<T>) {
        mData.apply {
            clear()
            addAll(data)
        }
    }

    override fun getItemCount(): Int = mData.size
    override fun onBindViewHolder(holder: BaseViewHolder<T>?, position: Int) {
        holder!!.bindData(mData[position])
    }

    override abstract fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<T>

}