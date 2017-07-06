package com.coder.seadoc.module.docgroup

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coder.seadoc.R
import com.coder.seadoc.base.BaseFragment
import com.coder.seadoc.model.Classify
import com.coder.seadoc.module.docgroup.core.DocGroupContract
import com.coder.seadoc.module.docgroup.core.DocGroupPresenter
import com.coder.seadoc.module.docgroup.di.DocGroupModule
import kotlinx.android.synthetic.main.fragment_doc_group.*
import javax.inject.Inject

/**
 * Created by liuting on 17/6/27.
 */
class DocGroupFragment : DocGroupContract.View, BaseFragment() {


    @Inject lateinit var mPresenter: DocGroupPresenter

    internal var mAdapter: TabPageIndicatorAdapter? = null

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_doc_group, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestComponent().plus(DocGroupModule(this)).inject(this)
        initView()
    }

    private fun initView() {
        showProgress()
        mPresenter.load()
    }

    override fun setGroupData(arrs: ArrayList<Classify>) {
        hideProgress()
        mAdapter = TabPageIndicatorAdapter(childFragmentManager, arrs)
        viewpager.apply {
            adapter = mAdapter
            offscreenPageLimit = arrs.size
        }
        tab_layout.setupWithViewPager(viewpager)
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