package com.coder.seadoc.module.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coder.seadoc.R
import com.coder.seadoc.base.BaseFragment

/**
 * Created by liuting on 17/6/30.
 */
class NewsListFragment :BaseFragment(){

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_news_list,container,false)
    }
}