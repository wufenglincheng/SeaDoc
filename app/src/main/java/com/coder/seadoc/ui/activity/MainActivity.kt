package com.coder.seadoc.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.coder.seadoc.R
import com.coder.seadoc.ResUtil
import com.coder.seadoc.ui.BaseActivity
import com.coder.seadoc.ui.fragment.DocGroupFragment
import com.coder.seadoc.utils.bindView

/**
 * Created by liuting on 17/6/27.
 */

class MainActivity : BaseActivity() {

    val viewPager: ViewPager by bindView(R.id.viewpager)
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
            add(DocGroupFragment())
            add(DocGroupFragment())
            add(DocGroupFragment())
        }
    }

    private fun initView() {
        viewPager.apply {
            adapter = object : FragmentPagerAdapter(supportFragmentManager) {
                override fun getItem(position: Int): Fragment = fragments[position]

                override fun getCount(): Int = fragments.size
            }
            offscreenPageLimit = fragments.size

        }
    }

    override fun onNavigationClickListener() {

    }

}
