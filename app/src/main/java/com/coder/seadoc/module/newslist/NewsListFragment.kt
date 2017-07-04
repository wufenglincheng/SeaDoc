package com.coder.seadoc.module.newslist

import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.support.graphics.drawable.VectorDrawableCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coder.seadoc.R
import com.coder.seadoc.base.BaseFragment

/**
 * Created by liuting on 17/6/30.
 */
class NewsListFragment : BaseFragment() {

//    val tabView:TabView by bindView(R.id.tabView)

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        tabView.setImages(arrayOf(R.drawable.ic_new_selected,R.drawable.ic_doc_selected,R.drawable.ic_people_selected))

    }
}