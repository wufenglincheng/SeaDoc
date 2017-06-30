package com.coder.seadoc.module.docgroup.core

import com.coder.seadoc.model.Classify

/**
 * Created by liuting on 17/6/27.
 */
open class DocGroupContract {
    interface View {
        fun setGroupData(arrs:ArrayList<Classify>)
    }
}