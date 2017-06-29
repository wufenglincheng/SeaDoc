package com.coder.seadoc.presenter.attacher

import com.coder.seadoc.model.Classify

/**
 * Created by liuting on 17/6/27.
 */
open class DocGroupAttacher {
    interface View {
        fun setGroupData(arrs:ArrayList<Classify>)
    }
}