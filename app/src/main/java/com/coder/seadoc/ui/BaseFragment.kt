package com.coder.seadoc.ui

import android.support.v4.app.Fragment
import com.coder.seadoc.MyApplication

/**
 * Created by liuting on 17/6/27.
 */
open class BaseFragment : Fragment() {

    fun getApplicaton(): MyApplication = activity.application as MyApplication
}