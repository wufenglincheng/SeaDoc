package com.coder.seadoc.model

import com.coder.seadoc.ResUtil

/**
 * Created by liuting on 17/7/6.
 */
data class NewsListResult(var page: NewsListPage, var value: ArrayList<NewsListItem>)

data class NewsListPage(var currentPage: Int,
                        var totalPage: Int,
                        var pageSize: Int,
                        var start: Int,
                        var end: Int,
                        var showRows: Int,
                        var sumRows: Int)

data class NewsListItem(var id: Int,
                        var href: String,
                        var mainTitle: String,
                        var subTitle: String,
                        var author: String,
                        var writeTime: String,
                        var imgUrl: String,
                        var imgWidth: String,
                        var imgHeight: String) {
    var showTime: String? = null

    fun praseData() {
        try {
            showTime = ResUtil.instance?.toDateText(writeTime.toLong())
        } catch (e: Exception) {

        }

    }
}