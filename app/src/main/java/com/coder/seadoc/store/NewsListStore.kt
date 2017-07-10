package com.coder.seadoc.store

import com.coder.seadoc.api.DocAPI
import com.coder.seadoc.model.NewsListItem
import rx.Observable

/**
 * Created by liuting on 17/7/6.
 */
class NewsListStore constructor(val api: DocAPI) {
    private var currentPage = 1
    fun loadNew(): Observable<ArrayList<NewsListItem>> {
        currentPage = 1
        return load()
    }

    fun loadMore(): Observable<ArrayList<NewsListItem>> {
        return load()
    }

    private fun load(): Observable<ArrayList<NewsListItem>> {
        val param = HashMap<String, String>()
        param.put("pageSize", "10")
        param.put("currentPage", currentPage.toString())
        return api.getNewsList(param)
                .flatMap {
                    obj ->
                    currentPage++
                    Observable.just(obj.value)
                }
    }

}