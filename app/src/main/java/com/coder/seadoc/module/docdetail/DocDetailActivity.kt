package com.coder.seadoc.module.docdetail

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.text.Html
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.EditText
import android.widget.TextView
import com.coder.seadoc.R
import com.coder.seadoc.base.BaseActivity
import com.coder.seadoc.model.BlogListItem
import com.coder.seadoc.module.docdetail.core.DocDetailContract
import com.coder.seadoc.module.docdetail.core.DocDetailPresenter
import com.coder.seadoc.module.docdetail.di.DocDetailModule
import com.coder.seadoc.utils.MyObject
import com.coder.seadoc.utils.dpToPx
import com.coder.seadoc.utils.localUri
import com.coder.seadoc.views.LoadingDrawable
import kotlinx.android.synthetic.main.activity_doc_detail.*
import javax.inject.Inject

/**
 * Created by liuting on 17/6/28.
 */

class DocDetailActivity : DocDetailContract.ActivityView, BaseActivity() {

    companion object {
        const val MODE_ID: String = "moduleId"
        const val MODE_NAME: String = "projectModuleName"
        const val LANGUNE_ONLY_CN = 0
        const val LANGUNE_ONLY_EN = 1
        const val LANGUNE_CN_EN = 2
    }


    lateinit var mDrawerToggle: ActionBarDrawerToggle
    lateinit var languageControl: MyObject
    lateinit var blogListFragment: BlogListFragment

    @Inject lateinit var mPresenter: DocDetailPresenter
    private lateinit var loadDrawable: LoadingDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc_detail)
        requestComponent().plus(DocDetailModule(this)).inject(this)
        initView()
        initData()
    }

    private fun initData() {
        val moduleId = intent.getIntExtra(MODE_ID, 0)
        showProgress()
        mPresenter.loadMenu(moduleId.toString())
    }



    fun initView() {
        val moduleName = intent.getStringExtra(MODE_NAME)
        setUpToolbar(moduleName, resources.getDrawable(R.drawable.ic_menu))
        wv_menu.apply {
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    blogListFragment.clearData()
                    loadDrawable.start()
                    mPresenter.loadPageData(url)
                    drawer_layout.closeDrawers()
                    return true
                }
                override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
                    var response = super.shouldInterceptRequest(view, url)
                    return localUri(response, url, assets)
                }
            })
        }
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar, 0, 0) {
            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
            }
        }
        mDrawerToggle.syncState()
        drawer_layout.setDrawerListener(mDrawerToggle)

        content_webview.apply {
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    if (url.startsWith("translate://")) {
                        mPresenter.LeftUrlLoading(url)
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
        languageControl = MyObject(content_webview, Handler())

        blogListFragment = BlogListFragment()
        supportFragmentManager.beginTransaction().replace(R.id.blog_parent, blogListFragment).commit()
        loadDrawable = LoadingDrawable(dpToPx(56, this))
        bt_to_blog_list.setImageDrawable(loadDrawable)
        bt_to_blog_list.postDelayed({ loadDrawable?.start() }, 200)
        bt_to_blog_list.setOnClickListener {
            if(!loadDrawable.isLoading()){
                blog_parent.visibility = View.VISIBLE
                blogListFragment.show(it, blog_parent)
            }
        }
    }

    override fun setMenuData(content: String?) {
        wv_menu.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
    }

    override fun setPageData(data: String) {
        hideProgress()
        content_webview.stopLoading()
        content_webview.loadDataWithBaseURL(null, data, "text/html", "utf-8", null)
    }

    override fun onBackPressed() {
        if (blog_parent.visibility == View.VISIBLE) {
            blogListFragment.hide()
        } else {
            super.onBackPressed()
        }
    }

    override fun setBlogList(list: ArrayList<BlogListItem>) {
        loadDrawable?.stop()
        blogListFragment.updateData(list)
    }

    override fun showTranlateDialog(content1: String?, content2: String?) {
        val dialogView: View = LayoutInflater.from(this).inflate(R.layout.alert_translate, null)
        val editText: EditText = dialogView.findViewById(R.id.tv_after_translate) as EditText
        val textView: TextView = dialogView.findViewById(R.id.tv_pre_translate) as TextView
        val confirm = dialogView.findViewById(R.id.btn_confirm_translate)
        val cancel = dialogView.findViewById(R.id.btn_cancel_translate)
        textView.text = Html.fromHtml(content1)
        editText.setText(content2)
        val dialog = AlertDialog.Builder(this)
                .setView(dialogView).create()
        confirm.setOnClickListener {
            mPresenter.translate(editText.text.toString())
            dialog.dismiss()
        }
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_doc_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_only_cn -> {
                changeLanguage(LANGUNE_ONLY_CN)
            }
            R.id.action_only_en -> {
                changeLanguage(LANGUNE_ONLY_EN)
            }
            R.id.action_cn_en -> {
                changeLanguage(LANGUNE_CN_EN)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeLanguage(type: Int) {
        when (type) {
            DocDetailActivity.LANGUNE_ONLY_CN -> {
                languageControl.chineseOnly()
            }
            DocDetailActivity.LANGUNE_ONLY_EN -> {
                languageControl.englishOnly()
            }
            DocDetailActivity.LANGUNE_CN_EN -> {
                languageControl.showAll()
            }
        }
    }

}
