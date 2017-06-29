package com.coder.seadoc.ui.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coder.seadoc.R
import com.coder.seadoc.di.DocGroupModule
import com.coder.seadoc.model.Classify
import com.coder.seadoc.presenter.DocGroupPresenter
import com.coder.seadoc.presenter.attacher.DocGroupAttacher
import com.coder.seadoc.ui.BaseFragment
import com.coder.seadoc.utils.bindView
import javax.inject.Inject

/**
 * Created by liuting on 17/6/27.
 */
class DocGroupFragment : DocGroupAttacher.View, BaseFragment() {

    val tabLayout: TabLayout by bindView(R.id.tab_layout)
    val viewPager: ViewPager by bindView(R.id.viewpager)

    @Inject lateinit var mPresenter: DocGroupPresenter

    internal var mAdapter: TabPageIndicatorAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_doc_group, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getApplicaton().appComponent.plus(DocGroupModule(this)).inject(this)
        initView()
    }

    private fun initView() {
        mPresenter.load()
    }

    override fun setGroupData(arrs: ArrayList<Classify>) {
        mAdapter = TabPageIndicatorAdapter(childFragmentManager, arrs)
        viewPager.apply {
            adapter = mAdapter
            offscreenPageLimit = arrs.size
        }
        tabLayout.setupWithViewPager(viewPager)
    }

    internal inner class TabPageIndicatorAdapter(fm: FragmentManager, private val list: ArrayList<Classify>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return DocListFragment.newInstance(list[position].id)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return list[position].name
        }

        override fun getCount(): Int {
            return list.size
        }
    }
}