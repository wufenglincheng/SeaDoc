package com.coder.seadoc.module.docdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.coder.seadoc.R
import com.coder.seadoc.model.BlogListItem
import com.coder.seadoc.utils.ViewHolder
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter

/**
 * Created by liuting on 17/7/25.
 */
class BlogListAdapter constructor(context: Context) : RecyclerArrayAdapter<BlogListItem>(context) {

    override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<BlogListItem> {
        var view = LayoutInflater.from(context).inflate(R.layout.item_blog_list, parent, false)
        return object : BaseViewHolder<BlogListItem>(view) {
            override fun setData(data: BlogListItem) {
                bindData(itemView, data)
            }
        }
    }

    private fun bindData(itemView: View, data: BlogListItem) {
        val holder = ViewHolder(itemView)
        holder.get<TextView>(R.id.title).text = data.blogTitle
        holder.get<TextView>(R.id.author).text = "—— " + data.blogAuthor
        holder.get<TextView>(R.id.time).text = data.blogTime
        holder.get<TextView>(R.id.source).text = "来源：" + data.blogTag
    }
}