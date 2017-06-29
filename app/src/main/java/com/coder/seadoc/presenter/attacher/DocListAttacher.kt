package com.coder.seadoc.presenter.attacher

import com.coder.seadoc.model.ProjectModule

/**
 * Created by liuting on 17/6/28.
 */
open class DocListAttacher {
    interface View {
        fun setListView(projects: ArrayList<ProjectModule>)
    }
}