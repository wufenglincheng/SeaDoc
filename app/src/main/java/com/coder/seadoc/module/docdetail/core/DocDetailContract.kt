package com.coder.seadoc.module.docdetail.core

import com.coder.seadoc.model.BlogListItem
import com.coder.seadoc.model.BlogPage
import com.coder.seadoc.model.BlogPageDetail

/**
 * Created by liuting on 17/6/28.
 */
open class DocDetailContract {
    interface ActivityView : DetailView {
        fun setMenuData(content: String?)
        fun setPageData(content: String)
        fun showTranlateDialog(content1: String?, content2: String?)
        fun setBlogList(list:ArrayList<BlogListItem>)
    }

    interface FragmentView : DetailView {
        fun setPageData(data: String?)
    }

    interface DetailView


}