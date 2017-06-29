package com.coder.seadoc.presenter

import android.util.Log
import com.coder.seadoc.model.BlogPage
import com.coder.seadoc.presenter.attacher.DocDetailAttacher
import com.coder.seadoc.store.DocDetailStore
import org.jsoup.Jsoup
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by liuting on 17/6/28.
 * 文档详情页的Presenter
 */
class DocDetailPresenter
@Inject
constructor(view: DocDetailAttacher.DetailView, val mStore: DocDetailStore) : BasePresenter() {
    var mView: DocDetailAttacher.ActivityView? = null

    init {
        mView = view as DocDetailAttacher.ActivityView
    }

    fun loadMenu(moduleId: String) {
        mStore.getMenu(moduleId)
                .map {
                    //装换对象为content
                    obj ->
                    obj.entryMenuFinal?.content
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    obj ->
                    mView?.setMenuData(obj)
                }
                .observeOn(Schedulers.io())
                .map {
                    obj ->
                    val parse = Jsoup.parse(obj)
                    val aElements = parse.select("a")
                    aElements
                }
                .filter {
                    obj ->
                    obj.size > 0 && obj[0] != null
                }
                .map {
                    obj ->
                    obj[0].attr("href")
                }
                .filter {
                    obj ->
                    obj != null && obj.startsWith("http")
                }
                .flatMap {
                    obj ->
                    mStore.getPageData(obj)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ArrayList<BlogPage>>() {

                    override fun onNext(t: ArrayList<BlogPage>) {
                        Log.e("DocDetailPresenter", t.toString())
                        mView?.setPageData(t)
                    }

                    override fun onError(e: Throwable?) {
                    }

                    override fun onCompleted() {

                    }
                })

    }

    fun loadPageData(url: String) {
        mStore.getPageData(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ArrayList<BlogPage>>() {

                    override fun onNext(t: ArrayList<BlogPage>) {
                        Log.e("DocDetailPresenter", t.toString())
                        mView?.setPageData(t)
                    }

                    override fun onError(e: Throwable?) {
                    }

                    override fun onCompleted() {

                    }
                })
    }
}