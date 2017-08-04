package com.coder.seadoc.module.docdetail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coder.seadoc.R
import com.coder.seadoc.base.BaseFragment
import com.coder.seadoc.model.BlogListItem
import kotlinx.android.synthetic.main.fragment_blog_list.*

/**
 * Created by liuting on 17/7/28.
 */
class BlogListFragment : BaseFragment() {
    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_blog_list, container, false)
    }

    private var parentView: View? = null
    lateinit var mBlogAdapter: BlogListAdapter
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        blog_list.apply {
            setLayoutManager(LinearLayoutManager(activity))
            mBlogAdapter = BlogListAdapter(activity)
            adapter = mBlogAdapter
        }
        blog_list.alpha = 0f
        bt_close.alpha = 0f
        title_sp.alpha = 0f
        bt_close.setOnClickListener {
            hide()
        }
    }

    fun updateData(list: ArrayList<BlogListItem>) {
        mBlogAdapter.clear()
        mBlogAdapter.addAll(list)
        mBlogAdapter.notifyDataSetChanged()
    }

    fun show(view: View, parentView: View) {
        this.parentView = parentView
        path_view.setView(view)
        path_view.setExpanded(true)
        bt_close.postDelayed({
            bt_close.animate().alpha(1f).setDuration(100).start()
            blog_list.animate().alpha(1f).setDuration(100).start()
            title_sp.animate().alpha(1f).setDuration(100).start()
        }, 300)
    }

    fun hide() {
        bt_close.animate().alpha(0f).setDuration(100).start()
        blog_list.animate().alpha(0f).setDuration(100).start()
        title_sp.animate().alpha(0f).setDuration(100).start()
        path_view.setExpanded(false)
        bt_close.postDelayed({ parentView?.visibility = View.GONE }, 300)
    }

    fun clearData() {
        mBlogAdapter.clear()
    }
}