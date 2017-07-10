package com.coder.seadoc.module.newsdetail.core

import android.text.Html
import android.text.Spannable
import android.text.Spanned
import android.text.TextUtils
import com.coder.seadoc.store.NewsDetailStore
import org.jsoup.Jsoup
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
                .map {
                    v ->
                    val document = Jsoup.parse(v)
                    Html.fromHtml( document.body().toString())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Spanned>() {
                    override fun onNext(t: Spanned?) {
                        if (!TextUtils.isEmpty(t)) mView.setPageData(t!!)
                    }

                    override fun onError(e: Throwable?) {
                    }

                    override fun onCompleted() {
                    }

                })
    }
}