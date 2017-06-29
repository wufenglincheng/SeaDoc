package com.coder.seadoc.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.coder.seadoc.R
import com.coder.seadoc.di.DocDetailModule
import com.coder.seadoc.model.BlogPage
import com.coder.seadoc.presenter.DocDetailPresenter
import com.coder.seadoc.presenter.attacher.DocDetailAttacher
import com.coder.seadoc.ui.BaseActivity
import com.coder.seadoc.ui.fragment.DocLeftFragment
import com.coder.seadoc.utils.bindView
import javax.inject.Inject

/**
 * Created by liuting on 17/6/28.
 */

class DocDetailActivity : DocDetailAttacher.ActivityView, BaseActivity() {

    companion object {
        const val MODE_ID: String = "moduleId"
        const val MODE_NAME: String = "projectModuleName"
    }

    val wv_menu: WebView by bindView(R.id.wv_menu)
    val indicator: TabLayout by bindView(R.id.indicator)
    val viewPager: ViewPager by bindView(R.id.viewpager)
    val drawerLayout: DrawerLayout by bindView(R.id.drawer_layout)

    val listFragment: ArrayList<Fragment> = ArrayList()
    lateinit var mDrawerToggle: ActionBarDrawerToggle

    @Inject lateinit var mPresenter: DocDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc_detail)
        getApplicaton().appComponent.plus(DocDetailModule(this)).inject(this)
        initView()
        initData()
    }

    private fun initData() {
        val moduleId = intent.getIntExtra(MODE_ID, 0)
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
                return true
            }
        })
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0) {
            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
            }
        }
        mDrawerToggle.syncState()
        drawerLayout.setDrawerListener(mDrawerToggle)

    }

    override fun setMenuData(content: String?) {
        wv_menu.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
    }

    override fun setPageData(arrs: ArrayList<BlogPage>) {
        viewPager.apply {
            offscreenPageLimit = arrs.size
            adapter = TabPageAdapter(supportFragmentManager).apply {
                setList(arrs)
            }
            adapter.notifyDataSetChanged()
        }
        indicator.apply {
            setupWithViewPager(viewPager)
            tabMode = TabLayout.MODE_SCROLLABLE
        }
    }

    internal inner class TabPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private var list: List<BlogPage> = java.util.ArrayList<BlogPage>()

        fun setList(list: List<BlogPage>) {
            this.list = list
        }

        override fun getItem(position: Int): Fragment {
            //新建一个Fragment来展示ViewPager item的内容，并传递参数
            val args = Bundle()
            val fragment: Fragment = DocLeftFragment()
            listFragment.add(fragment)
            fragment.arguments = args
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

        override fun getItemPosition(`object`: Any?): Int {
            if (list.size > 0) {
                return PagerAdapter.POSITION_NONE
            }
            return super.getItemPosition(`object`)

        }
    }
}