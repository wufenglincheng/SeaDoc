package com.coder.seadoc.module.newslist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coder.seadoc.R
import com.coder.seadoc.base.BaseFragment
import com.coder.seadoc.model.NewsListItem
import com.coder.seadoc.module.newslist.core.NewsListContract
import com.coder.seadoc.module.newslist.core.NewsListPresenter
import com.coder.seadoc.module.newslist.di.NewsListModule
import com.coder.seadoc.utils.ViewHolder
import com.coder.seadoc.utils.requestScreenWidth
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import kotlinx.android.synthetic.main.fragment_news_list.*
import javax.inject.Inject

/**
 * Created by liuting on 17/6/30.
 */
class NewsListFragment : NewsListContract.View, BaseFragment() {

    @Inject
    lateinit var mPresenter: NewsListPresenter
    lateinit var mAdapter: RecyclerArrayAdapter<NewsListItem>

    override fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestComponent().plus(NewsListModule(this)).inject(this)
        initView()
        mPresenter.loadNew()
    }

    private fun initView() {
        mAdapter = object : RecyclerArrayAdapter<NewsListItem>(activity) {

            override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NewsListItem> {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_news_list, parent, false)
                return object : BaseViewHolder<NewsListItem>(view) {
                    override fun setData(data: NewsListItem?) {
                        bindData(data, ViewHolder(itemView))
                    }
                }
            }

            private fun bindData(data: NewsListItem?, holder: ViewHolder) {
                holder.get<TextView>(R.id.item_title).text = data?.mainTitle
                holder.get<TextView>(R.id.item_sub_title).text = data?.subTitle
                holder.get<TextView>(R.id.item_author).text = data?.author
                holder.get<TextView>(R.id.item_time).text = data?.showTime
                val height = (requestScreenWidth() * 9f / 16).toInt()
                val cover: ImageView = holder.get(R.id.item_cover)
                cover.apply {
                    layoutParams.height = height
                    layoutParams = layoutParams
                }
                Glide.with(activity)
                        .load(data?.imgUrl)
                        .apply(RequestOptions().centerCrop())
                        .into(holder.get(R.id.item_cover))
            }

        }
        recyclerView.apply {
            setLayoutManager(LinearLayoutManager(activity))
            adapter = mAdapter
        }
    }

    override fun updateNewList(t: ArrayList<NewsListItem>) {
        mAdapter.addAll(t)
    }

}