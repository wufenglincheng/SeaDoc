package com.coder.seadoc.module.docdetail.core

import com.coder.seadoc.base.BasePresenter
import com.coder.seadoc.model.GetBlogPageDetail
import com.coder.seadoc.store.DocDetailStore
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

    fun loadData(blog_id: Int) {
        mStore.getRelativeBlogContent(blog_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Subscriber<GetBlogPageDetail>() {
                            override fun onCompleted() {
                            }

                            override fun onNext(t: GetBlogPageDetail?) {
                                if (t?.blogPageDetail != null)
                                    mView?.setPageData(t.blogPageDetail!!.pageContent)
                                else
                                    onError(null)
                            }

                            override fun onError(e: Throwable?) {
                            }

                        }
                )
    }

}