package com.coder.seadoc.module.docdetail

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.coder.seadoc.R
import com.coder.seadoc.base.BaseActivity
import com.coder.seadoc.model.BlogPage
import com.coder.seadoc.module.docdetail.core.DocDetailContract
import com.coder.seadoc.module.docdetail.core.DocDetailPresenter
import com.coder.seadoc.module.docdetail.di.DocDetailModule
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


    val listFragment: ArrayList<Fragment> = ArrayList()
    lateinit var mDrawerToggle: ActionBarDrawerToggle

    @Inject lateinit var mPresenter: DocDetailPresenter

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
        val webSettings = wv_menu.settings
        webSettings.javaScriptEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        wv_menu.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                mPresenter.loadPageData(url)
                drawer_layout.closeDrawers()
                return true
            }
        })
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

    }

    override fun setMenuData(content: String?) {
        wv_menu.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
    }

    override fun setPageData(arrs: ArrayList<BlogPage>) {
        hideProgress()
        listFragment.clear()
        viewpager.apply {
            offscreenPageLimit = arrs.size
            adapter = TabPageAdapter(supportFragmentManager).apply {
                setList(arrs)
            }
            adapter.notifyDataSetChanged()
        }
        indicator.apply {
            setupWithViewPager(viewpager)
            tabMode = TabLayout.MODE_SCROLLABLE
        }
    }

    internal inner class TabPageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        private var list: List<BlogPage> = java.util.ArrayList<BlogPage>()

        fun setList(list: List<BlogPage>) {
            this.list = list
        }

        override fun getItem(position: Int): Fragment {
            //新建一个Fragment来展示ViewPager item的内容，并传递参数
            val fragment: Fragment
            if (position == 0) {
                fragment = DocLeftFragment()
            } else {
                fragment = DocRightFragment.newInstance(position)
            }
            listFragment.add(fragment)
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence {
            if (position == 0) {
                return "原文"
            }
            return list[position].blogTitle + ""
        }

        override fun getCount(): Int {
            return list.size
        }
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

    private fun changeLanguage(type:Int) {
        var fragment = listFragment[viewpager.currentItem]
        if (fragment is DocLeftFragment) {
            fragment.showLanguage(type)
        }
        if (fragment is DocRightFragment) {
            fragment.showLanguage(type)
        }
    }

}
