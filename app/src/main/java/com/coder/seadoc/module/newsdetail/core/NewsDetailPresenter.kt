package com.coder.seadoc.module.newsdetail.core

import android.text.TextUtils
import com.coder.seadoc.store.NewsDetailStore
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by liuting on 17/7/7.
 */
class NewsDetailPresenter
@Inject
constructor(val mView: NewsDetailContract.View, val mStore: NewsDetailStore) {
    fun load(id: Int) {
        mStore.load(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<String>() {
                    override fun onNext(t: String?) {
                        if (!TextUtils.isEmpty(t)) mView.setPageData(t!!)
                    }

                    override fun onError(e: Throwable?) {
                    }

                    override fun onCompleted() {
                    }

                })
    }
}