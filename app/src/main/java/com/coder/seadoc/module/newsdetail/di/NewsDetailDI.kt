package com.coder.seadoc.module.newsdetail.di

import com.coder.seadoc.api.DocAPI
import com.coder.seadoc.module.newsdetail.NewsDetailActivity
import com.coder.seadoc.module.newsdetail.core.NewsDetailContract
import com.coder.seadoc.store.NewsDetailStore
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Created by liuting on 17/7/7.
 */
@Subcomponent(modules = arrayOf(NewsDetailModule::class))
interface NewsDetailComponent {
    fun inject(activity: NewsDetailActivity)
}

@Module
class NewsDetailModule constructor(val mView: NewsDetailContract.View) {
    @Provides
    fun provideView() = mView

    @Provides
    fun provideStore(api: DocAPI) = NewsDetailStore(api)
}