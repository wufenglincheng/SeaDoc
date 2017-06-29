package com.coder.seadoc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.coder.seadoc.R
import com.coder.seadoc.di.DocDetailModule
import com.coder.seadoc.model.BlogPageDetail
import com.coder.seadoc.presenter.DocDetailFragmentPresenter
import com.coder.seadoc.presenter.attacher.DocDetailAttacher
import com.coder.seadoc.ui.BaseFragment
import com.coder.seadoc.utils.bindView
import javax.inject.Inject

/**
 * Created by liuting on 17/6/29.
 */
class DocLeftFragment : DocDetailAttacher.FragmentView, BaseFragment() {

    val webView: WebView by bindView(R.id.webview_doc)

    @Inject lateinit var mPresent: DocDetailFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_doc_left, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getApplicaton().appComponent.plus(DocDetailModule(this)).inject(this)
        webView.settings.apply {
            javaScriptEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
        }
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return super.shouldOverrideUrlLoading(view, url)
            }
        })
        mPresent.load()
    }

    override fun setPageData(data: BlogPageDetail) {
        webView.loadDataWithBaseURL(null, data.pageContent, "text/html", "utf-8", null)
    }
}