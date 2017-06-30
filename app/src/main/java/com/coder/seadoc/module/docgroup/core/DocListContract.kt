package com.coder.seadoc.module.docgroup.core

import com.coder.seadoc.model.ProjectModule

/**
 * Created by liuting on 17/6/28.
 */
open class DocListContract {
    interface View {
        fun setListView(projects: ArrayList<ProjectModule>)
    }
}