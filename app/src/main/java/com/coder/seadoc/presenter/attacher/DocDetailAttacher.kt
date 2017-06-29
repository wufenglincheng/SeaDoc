package com.coder.seadoc.presenter.attacher

import com.coder.seadoc.model.BlogPage
import com.coder.seadoc.model.BlogPageDetail

/**
 * Created by liuting on 17/6/28.
 */
open class DocDetailAttacher {
    interface ActivityView : DetailView {
        fun setMenuData(content: String?)
        fun setPageData(arrs: ArrayList<BlogPage>)
    }

    interface FragmentView : DetailView {
        fun setPageData(data: BlogPageDetail)
    }

    interface DetailView
}