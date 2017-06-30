package com.coder.seadoc.module.docdetail

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.TextView
import com.coder.seadoc.R
import com.coder.seadoc.base.BaseFragment
import com.coder.seadoc.module.docdetail.core.DocDetailContract
import com.coder.seadoc.module.docdetail.core.DocDetailFragmentPresenter
import com.coder.seadoc.module.docdetail.di.DocDetailModule
import com.coder.seadoc.utils.bindView
import javax.inject.Inject

/**
 * Created by liuting on 17/6/29.
 */
class DocLeftFragment : DocDetailContract.FragmentView, BaseFragment() {

    val webView: WebView by bindView(R.id.webview_doc)

    @Inject
    lateinit var mPresent: DocDetailFragmentPresenter

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
            setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    if (url.startsWith("translate://")) {
                        mPresent.LeftUrlLoading(url)
                        return true
                    }
                    return super.shouldOverrideUrlLoading(view, url)
                }
            })
            setWebChromeClient(object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (newProgress == 100) hideProgress()
                }
            })
        }
        mPresent.loadLeft()
    }

    override fun setPageData(data: String?) {
        webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null)
    }

    override fun showTranlateDialog(content1: String?, content2: String?) {
        val dialogView: View = LayoutInflater.from(activity).inflate(R.layout.alert_translate, null)
        val editText: EditText = dialogView.findViewById(R.id.tv_after_translate) as EditText
        val textView: TextView = dialogView.findViewById(R.id.tv_pre_translate) as TextView
        val confirm = dialogView.findViewById(R.id.btn_confirm_translate)
        val cancel = dialogView.findViewById(R.id.btn_cancel_translate)
        textView.text = Html.fromHtml(content1)
        editText.setText(content2)
        val dialog = AlertDialog.Builder(activity)
                .setView(dialogView).create()
        confirm.setOnClickListener {
            v ->
            mPresent.translate(editText.text.toString())
            dialog.dismiss()
        }
        cancel.setOnClickListener {
            v ->
            dialog.dismiss()
        }
        dialog.show()
    }
}