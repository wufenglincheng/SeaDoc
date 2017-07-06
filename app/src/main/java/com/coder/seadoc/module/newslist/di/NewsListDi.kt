package com.coder.seadoc.module.newslist.di

import com.coder.seadoc.api.DocAPI
import com.coder.seadoc.module.newslist.NewsListFragment
import com.coder.seadoc.module.newslist.core.NewsListContract
import com.coder.seadoc.store.NewsListStore
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by liuting on 17/7/6.
 */
@Subcomponent(modules = arrayOf(NewsListModule::class))
interface NewsListComponent {
    fun inject(fragment: NewsListFragment)
}

@Module
class NewsListModule constructor(private val mView: NewsListContract.View) {
    @Provides
    fun provideView() = mView

    @Provides
    fun provideStore(api: DocAPI) = NewsListStore(api)
}