package com.coder.seadoc.module.newslist.core

import com.coder.seadoc.model.NewsListItem

/**
 * Created by liuting on 17/7/6.
 */
class NewsListContract{
    interface View{
        fun updateNewList(t: ArrayList<NewsListItem>)
    }
}