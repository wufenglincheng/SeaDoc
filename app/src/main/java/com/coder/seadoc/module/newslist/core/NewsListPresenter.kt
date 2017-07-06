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
    fun loadNew() {
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
                    }

                    override fun onError(e: Throwable?) {

                    }

                    override fun onCompleted() {
                    }

                })
    }
}