package com.coder.seadoc.module

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import com.coder.seadoc.R
import com.coder.seadoc.ResUtil
import com.coder.seadoc.base.BaseActivity
import com.coder.seadoc.module.docgroup.DocGroupFragment
import com.coder.seadoc.module.newslist.NewsListFragment
import com.coder.seadoc.utils.bindView

/**
 * Created by liuting on 17/6/27.
 */

class MainActivity : BaseActivity() {

    companion object {
        private val TAB_NEWS = 0
        private val TAB_DOCS = 1
        private val TAB_PEOPLE = 2
    }

    val viewPager: ViewPager by bindView(R.id.viewpager)
    val tabNews: ImageView by bindView(R.id.tab_news)
    val tabDocs: ImageView by bindView(R.id.tab_docs)
    val tabPeople: ImageView by bindView(R.id.tab_people)

    val fragments: ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar("", ResUtil.instance!!.getDrawable(R.drawable.ic_menu))
        initFragments()
        initView()
    }

    private fun initFragments() {
        fragments.apply {
            add(NewsListFragment())
            add(DocGroupFragment())
            add(NewsListFragment())
        }
    }

    fun changeTab(tabIndex: Int) {
        tabNews.isSelected = (tabIndex == TAB_NEWS)
        tabDocs.isSelected = (tabIndex == TAB_DOCS)
        tabPeople.isSelected = (tabIndex == TAB_PEOPLE)
        viewPager.setCurrentItem(tabIndex, true)
    }

    private fun initView() {
        viewPager.apply {
            adapter = object : FragmentPagerAdapter(supportFragmentManager) {
                override fun getItem(position: Int): Fragment = fragments[position]

                override fun getCount(): Int = fragments.size
            }
            offscreenPageLimit = fragments.size
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                override fun onPageSelected(position: Int) {
                    changeTab(position)
                }

                override fun onPageScrollStateChanged(state: Int) {}

            })
        }
        changeTab(TAB_DOCS)
        tabNews.setOnClickListener { v -> changeTab(TAB_NEWS) }
        tabDocs.setOnClickListener { v -> changeTab(TAB_DOCS) }
        tabPeople.setOnClickListener { v -> changeTab(TAB_PEOPLE) }
    }
}
