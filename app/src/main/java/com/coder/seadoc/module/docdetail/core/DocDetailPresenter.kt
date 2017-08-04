package com.coder.seadoc.module.docdetail.core

import android.text.TextUtils
import android.util.Log
import com.coder.seadoc.base.BasePresenter
import com.coder.seadoc.model.BaseType
import com.coder.seadoc.model.BlogListItem
import com.coder.seadoc.store.DocDetailStore
import org.jsoup.Jsoup
import rx.Observer
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
constructor(view: DocDetailContract.DetailView, val mStore: DocDetailStore) : BasePresenter() {

    var mView: DocDetailContract.ActivityView? = null

    init {
        mView = view as DocDetailContract.ActivityView
    }

    fun loadMenu(moduleId: String) {
        addSub(
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
                            for (item in obj) {

                            }
                            var url: String = ""
                            obj.forEachIndexed { index, element ->
                                if (!obj[index].attr("href").isNullOrBlank()) {
                                    url = obj[index].attr("href")
                                    return@forEachIndexed
                                }
                            }
                            url
                        }
                        .filter {
                            obj ->
                            obj != null && obj.startsWith("http")
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Subscriber<String?>() {
                            override fun onNext(t: String?) {
                                if (t != null) {
                                    loadPageData(t)
                                }
                            }

                            override fun onError(e: Throwable?) {
                            }

                            override fun onCompleted() {

                            }
                        })
        )

    }

    fun loadPageData(url: String) {
        loadBlogList(url)
        addSub(
                mStore.getPageData(url)
                        .map {
                            mStore.getBlogPageDetail()?.pageContent
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Subscriber<String?>() {

                            override fun onNext(t: String?) {
                                if (t != null) {
                                    Log.e("DocDetailPresenter", t.toString())
                                    mView?.setPageData(t)
                                } else {

                                }
                            }

                            override fun onError(e: Throwable?) {
                            }

                            override fun onCompleted() {

                            }
                        })
        )

    }

    fun loadBlogList(url: String) {
        addSub(mStore.loadBlogList(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ArrayList<BlogListItem>>() {
                    override fun onNext(t: ArrayList<BlogListItem>?) {
                        if (t != null) {
                            mView?.setBlogList(t)
                        }
                    }

                    override fun onError(e: Throwable?) {
                    }

                    override fun onCompleted() {
                    }

                }))
    }

    var tranlsateContent1: String? = null
    var tranlsateContent2: String? = null
    var tranlsateId: Int? = null
    var tranlsateContent3: String? = null
    var tranlsatebid: String? = null

    fun LeftUrlLoading(url: String) {
        tranlsateId = mStore.getBlogPageDetail()?.id
        tranlsatebid = url.substring(url.lastIndexOf("?") + 4, url.length)
        tranlsateContent3 = mStore.getBlogPageDetail()?.pageContent
        val document = Jsoup.parse(tranlsateContent3)
        val select1 = document.select("p[aid=$tranlsatebid]")
        val select2 = document.select("p[bid=$tranlsatebid]")
        val select3 = select2.select("a[class='translate']")
        if (select3.size > 0) {
            select3.remove()
        }
        tranlsateContent1 = select1.html()
        tranlsateContent2 = select2.html()
        mView?.showTranlateDialog(tranlsateContent1, tranlsateContent2)
    }

    fun translate(text: String) {
        if (!TextUtils.isEmpty(text)) run {
            val parse = Jsoup.parse(tranlsateContent3)
            val select = parse.select("p[bid=$tranlsatebid]")
            var finalStr = text
            if (select.size > 0) {
                finalStr += "<a class=\"translate\" href=\"translate://admin_translateByLabelId?id=$tranlsatebid\">译</a>"
                select.last().html(finalStr)
                mStore.updataBlogPageContent(parse.outerHtml())
                mView?.setPageData(parse.outerHtml())
            }
            mStore.translate(tranlsateId, tranlsatebid!!, finalStr)
                    .subscribe(object : Observer<BaseType> {
                        override fun onError(e: Throwable?) {}
                        override fun onNext(t: BaseType?) {}
                        override fun onCompleted() {}
                    })
        }
    }
}