package com.coder.seadoc.module.docdetail

import android.os.Bundle
import android.view.Menu
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.coder.seadoc.R
import com.coder.seadoc.base.BaseActivity
import com.coder.seadoc.module.docdetail.core.DocDetailContract
import com.coder.seadoc.module.docdetail.core.DocDetailFragmentPresenter
import com.coder.seadoc.module.docdetail.di.DocDetailModule
import kotlinx.android.synthetic.main.activity_blog_detail.*
import javax.inject.Inject

class BlogDetailActivity : DocDetailContract.FragmentView, BaseActivity() {
    companion object{
        val BLOG_ID = "blog_id"
    }
    @Inject
    lateinit var mPresent: DocDetailFragmentPresenter
    private var blog_id = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog_detail)
        requestComponent().plus(DocDetailModule(this)).inject(this)
        setUpToolbar("",resources.getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha))
        content_webview.settings.apply {
            javaScriptEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
        }
        showProgress()
        content_webview.apply {
            setWebViewClient(WebViewClient())
            setWebChromeClient(object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (newProgress == 100) hideProgress()
                }
            })
        }
        blog_id = intent.getIntExtra(BLOG_ID,-1)
        mPresent.loadData(blog_id)
    }

    override fun setPageData(data: String?) {
        content_webview.stopLoading()
        content_webview.loadDataWithBaseURL(null, data, "text/html", "utf-8", null)
    }
}
