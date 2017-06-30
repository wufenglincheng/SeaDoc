package com.coder.seadoc.module.docdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.coder.seadoc.R
import com.coder.seadoc.base.BaseFragment
import com.coder.seadoc.module.docdetail.core.DocDetailContract
import com.coder.seadoc.module.docdetail.core.DocDetailFragmentPresenter
import com.coder.seadoc.module.docdetail.di.DocDetailModule
import com.coder.seadoc.utils.bindView
import javax.inject.Inject

/**
 * Created by liuting on 17/6/30.
 */
class DocRightFragment : DocDetailContract.FragmentView, BaseFragment() {

    companion object {
        fun newInstance(position: Int): DocRightFragment {
            val fragment = DocRightFragment()
            fragment.position = position
            return fragment
        }
    }

    val webView: WebView by bindView(R.id.webview_doc)

    @Inject
    lateinit var mPresent: DocDetailFragmentPresenter

    var position: Int = 0

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_doc_left, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getApplicaton().appComponent.plus(DocDetailModule(this)).inject(this)
        webView.settings.apply {
            javaScriptEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
        }
        showProgress()
        webView.apply {
            setWebViewClient(WebViewClient())
            setWebChromeClient(object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (newProgress == 100) hideProgress()
                }
            })
        }
        mPresent.loadRight(position)
    }

    override fun setPageData(data: String?) {
        webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null)
    }

    override fun showTranlateDialog(content1: String?, content2: String?) {

    }
}