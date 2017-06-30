package com.coder.seadoc.module.docdetail.core

import android.text.TextUtils
import com.coder.seadoc.base.BasePresenter
import com.coder.seadoc.model.BaseType
import com.coder.seadoc.model.GetBlogPageDetail
import com.coder.seadoc.store.DocDetailStore
import org.jsoup.Jsoup
import rx.Observable
import rx.Observer
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by liuting on 17/6/29.
 */
class DocDetailFragmentPresenter
@Inject
constructor(view: DocDetailContract.DetailView, val mStore: DocDetailStore) : BasePresenter() {
    var mView: DocDetailContract.FragmentView? = null

    init {
        mView = view as DocDetailContract.FragmentView
    }

    fun loadLeft() {
        var data = mStore.getBlogPageDetail()
        if (data != null) {
            mView?.setPageData(data.pageContent)
        } else {

        }
    }

    fun loadRight(position: Int) {
        addSub(Observable.just(mStore.getBlogPage(position))
                .filter {
                    obj ->
                    obj != null
                }
                .flatMap {
                    obj ->
                    mStore.getRelativeBlogContent(obj!!.id)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Subscriber<GetBlogPageDetail>() {
                            override fun onCompleted() {
                            }

                            override fun onNext(t: GetBlogPageDetail?) {
                                if (t?.blogPageDetail != null)
                                    mView?.setPageData(t?.blogPageDetail!!.pageContent)
                                else
                                    onError(null)
                            }

                            override fun onError(e: Throwable?) {
                            }

                        }
                ))
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
                        override fun onError(e: Throwable?) {

                        }

                        override fun onNext(t: BaseType?) {

                        }

                        override fun onCompleted() {
                        }

                    })
        }
    }
}