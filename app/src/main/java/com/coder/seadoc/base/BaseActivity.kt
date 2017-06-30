package com.coder.seadoc.base

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.coder.seadoc.MyApplication
import com.coder.seadoc.R
import com.coder.seadoc.utils.bindView

/**
 * Created by liuting on 17/6/27.
 * Activity的基类
 */
open class BaseActivity : AppCompatActivity() {

    val toolbar: Toolbar by bindView(R.id.toolbar)
    var progressView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        val view: View? = LayoutInflater.from(this).inflate(layoutResID, null)
        progressView = LayoutInflater.from(this).inflate(R.layout.layout_progress, null)
        progressView?.visibility = View.GONE
        val parent: FrameLayout = FrameLayout(this)
        parent.addView(view)
        parent.addView(progressView)
        super.setContentView(parent)
    }

    /**
     * 注册toolbar
     */
    fun setUpToolbar(title: String, navigationIcon: Drawable) {
        toolbar?.apply {
            this.title = title
            this.navigationIcon = navigationIcon
            setTitleTextColor(Color.WHITE)
            setSupportActionBar(toolbar)
            setNavigationOnClickListener {
                v ->
                onNavigationClickListener()
            }
        }

    }

    /**
     * 默认的navigationIcon的点击事件
     */
    open fun onNavigationClickListener() {
        onBackPressed()
    }

    fun showProgress() {
        progressView?.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressView?.visibility = View.GONE
    }

    fun getApplicaton(): MyApplication = application as MyApplication
}
