package com.coder.seadoc.module.newslist.core

import com.coder.seadoc.model.NewsListItem
import com.coder.seadoc.store.NewsListStore
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by liuting on 17/7/6.
 */
class NewsListPresenter
@Inject
constructor(val mView: NewsListContract.View, val mStore: NewsListStore) {
    var isLoading = false
    fun loadNew() {
        if (isLoading) return
        isLoading = true
        mStore.loadNew()
                .doOnNext {
                    obj ->
                    for (a in obj)
                        a.praseData()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ArrayList<NewsListItem>>() {
                    override fun onNext(t: ArrayList<NewsListItem>?) {
                        if (t != null) mView.updateNewList(t!!)
                        else onError(null)
                    }

                    override fun onError(e: Throwable?) {
                        mView.setLoadingError()
                    }

                    override fun onCompleted() {
                        isLoading = false
                    }

                })
    }

    fun loadMore() {
        if (isLoading) return
        isLoading = true
        mStore.loadMore()
                .doOnNext {
                    obj ->
                    for (a in obj)
                        a.praseData()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ArrayList<NewsListItem>>() {
                    override fun onNext(t: ArrayList<NewsListItem>?) {
                        if (t != null) mView.updateMoreList(t!!)
                        else onError(null)
                    }

                    override fun onError(e: Throwable?) {
                        mView.setLoadingError()
                    }

                    override fun onCompleted() {
                        isLoading = false
                    }

                })
    }
}