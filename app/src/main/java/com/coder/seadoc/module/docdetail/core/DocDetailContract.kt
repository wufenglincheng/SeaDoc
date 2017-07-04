package com.coder.seadoc.module.docdetail.core

import com.coder.seadoc.model.BlogPage
import com.coder.seadoc.model.BlogPageDetail

/**
 * Created by liuting on 17/6/28.
 */
open class DocDetailContract {
    interface ActivityView : DetailView {
        fun setMenuData(content: String?)
        fun setPageData(arrs: ArrayList<BlogPage>)
    }

    interface FragmentView : DetailView {
        fun setPageData(data: String?)
        fun showTranlateDialog(content1: String?, content2: String?)
    }

    interface DetailView


}