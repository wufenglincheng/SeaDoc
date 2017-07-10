package com.coder.seadoc.module.newslist

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.coder.seadoc.R
import com.coder.seadoc.base.BaseFragment
import com.coder.seadoc.model.NewsListItem
import com.coder.seadoc.module.newsdetail.NewsDetailActivity
import com.coder.seadoc.module.newslist.core.NewsListContract
import com.coder.seadoc.module.newslist.core.NewsListPresenter
import com.coder.seadoc.module.newslist.di.NewsListModule
import com.coder.seadoc.utils.*
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import kotlinx.android.synthetic.main.fragment_news_list.*
import javax.inject.Inject

/**
 * Created by liuting on 17/6/30.
 */
class NewsListFragment : NewsListContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnMoreListener, BaseFragment() {

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
        showProgress()
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
                        .apply(RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(holder.get(R.id.item_cover))

                holder.get<ImageView>(R.id.item_cover).transitionName = data?.imgUrl
                holder.get<View>(R.id.item_parent).apply {
                    setOnClickListener {
                        val intent = Intent(activity, NewsDetailActivity::class.java)
                        intent.putExtra(IMAGE_URL, data?.imgUrl)
                        intent.putExtra(NEWS_ID, data?.id)
                        val options = ActivityOptions.makeSceneTransitionAnimation(activity,
                                Pair.create<View, String>(holder.get(R.id.item_cover), getString(R.string.transition_cover)),
                                Pair.create<View, String>(holder.get(R.id.item_cover), getString(R.string
                                        .transition_background)))
                        activity.startActivityForResult(intent, 5407, options.toBundle())
                    }
                }
            }

        }
        recyclerView.apply {
            setLayoutManager(LinearLayoutManager(activity))
            adapter = mAdapter
            setRefreshListener(this@NewsListFragment)
        }
        mAdapter.setMore(R.layout.view_load_more, this)
    }

    override fun onRefresh() {
        mPresenter.loadNew()
    }

    override fun onMoreShow() {
        mPresenter.loadMore()
    }

    override fun onMoreClick() {
        mPresenter.loadMore()
    }

    override fun updateNewList(t: ArrayList<NewsListItem>) {
        hideProgress()
        recyclerView.setRefreshing(false)
        mAdapter.clear()
        mAdapter.addAll(t)
    }

    override fun updateMoreList(t: ArrayList<NewsListItem>) {
        mAdapter.addAll(t)
    }

    override fun setLoadingError() {
        if (mAdapter.allData.size == 0)
            recyclerView.showError()
        else
            showToast("网络错误")
    }

}