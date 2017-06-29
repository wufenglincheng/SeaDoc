package com.coder.seadoc.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.coder.seadoc.MyApplication
import com.coder.seadoc.R
import com.coder.seadoc.utils.bindView

/**
 * Created by liuting on 17/6/27.
 * Activity的基类
 */
open class BaseActivity : AppCompatActivity() {

    val toolbar: Toolbar by bindView(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 注册toolbar
     */
    fun setUpToolbar(title: String, navigationIcon: Drawable) {
        toolbar?.apply {
            this.title = title
            this.navigationIcon = navigationIcon
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

    fun getApplicaton(): MyApplication = application as MyApplication
}
