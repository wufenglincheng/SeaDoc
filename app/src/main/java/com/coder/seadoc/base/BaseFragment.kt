package com.coder.seadoc.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.coder.seadoc.MyApplication
import com.coder.seadoc.R

/**
 * Created by liuting on 17/6/27.
 */
open abstract class BaseFragment : Fragment() {

    fun getApplicaton(): MyApplication = activity.application as MyApplication
    var progressView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = getLayoutView(inflater, container, savedInstanceState)
        progressView = inflater?.inflate(R.layout.layout_progress, null)
        progressView?.visibility = View.GONE
        val parent: FrameLayout = FrameLayout(container?.context)
        parent.addView(view)
        parent.addView(progressView)
        return parent
    }

    abstract fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?


    fun showProgress() {
        progressView?.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressView?.visibility = View.GONE
    }
}