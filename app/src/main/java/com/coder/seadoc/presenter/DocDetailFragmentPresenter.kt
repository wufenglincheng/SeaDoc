package com.coder.seadoc.presenter

import com.coder.seadoc.presenter.attacher.DocDetailAttacher
import com.coder.seadoc.store.DocDetailStore
import javax.inject.Inject

/**
 * Created by liuting on 17/6/29.
 */
class DocDetailFragmentPresenter
@Inject
constructor(view: DocDetailAttacher.DetailView, val mStore: DocDetailStore) {
    var mView: DocDetailAttacher.FragmentView? = null

    init {
        mView = view as DocDetailAttacher.FragmentView
    }

    fun load() {
        var data = mStore.getBlogPageDetail()
        if (data != null) {
            mView?.setPageData(data)
        } else {

        }
    }
}