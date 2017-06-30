package com.coder.seadoc.module.docgroup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.coder.seadoc.R
import com.coder.seadoc.module.docgroup.di.DocListModule
import com.coder.seadoc.model.ProjectModule
import com.coder.seadoc.module.docgroup.core.DocListPresenter
import com.coder.seadoc.module.docgroup.core.DocListContract
import com.coder.seadoc.base.BaseFragment
import com.coder.seadoc.module.docdetail.DocDetailActivity
import com.coder.seadoc.views.BaseViewHolder
import com.coder.seadoc.views.adapter.SimpleRecyclerAdapter
import com.coder.seadoc.utils.bindView
import javax.inject.Inject

/**
 * Created by liuting on 17/6/28.
 */
class DocListFragment : DocListContract.View, BaseFragment() {

    companion object {
        const val DOC_LIST_ID = "DOC_LIST_ID"
        fun newInstance(id: Int): DocListFragment {
            val fragment = DocListFragment()
            fragment.apply {
                arguments = Bundle().apply {
                    putInt(DOC_LIST_ID, id)
                }
            }
            return fragment
        }
    }

    val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    @Inject lateinit var mPresenter: DocListPresenter

    var mAdapter: DocListAdapter? = null

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_doc_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getApplicaton().appComponent.plus(DocListModule(this)).inject(this)
        var list_id = arguments[DOC_LIST_ID] as Int
        initView()
        mPresenter.loadList(list_id)
    }

    private fun initView() {
        mAdapter = DocListAdapter(context)
        recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun setListView(projects: ArrayList<ProjectModule>) {
        Log.e("setListView", projects?.toString())
        mAdapter!!.apply {
            setData(projects)
        }
        mAdapter!!.notifyDataSetChanged()
    }

    inner class DocListAdapter(context: Context) : SimpleRecyclerAdapter<ProjectModule>(context) {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<ProjectModule> {
            var view: View = LayoutInflater.from(context).inflate(R.layout.item_doc_list, parent, false)
            return DocListViewHolder(view)
        }
    }

    inner class DocListViewHolder(view: View) : BaseViewHolder<ProjectModule>(view) {

        override fun bindData(data: ProjectModule) {
            (bindView(R.id.item_title) as TextView).apply {
                text = data.displayName
            }
            (bindView(R.id.item_desc) as TextView).apply {
                text = data.describtion
            }
            bindView(R.id.item_parent)?.setOnClickListener {
                v ->
                var intent: Intent = Intent(activity, DocDetailActivity::class.java).apply {
                    putExtra(DocDetailActivity.MODE_ID, data.id)
                    putExtra(DocDetailActivity.MODE_NAME, data.moduleName)
                }
                activity.startActivity(intent)
            }
        }
    }
}