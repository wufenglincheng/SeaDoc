package com.coder.seadoc.base.di

import com.coder.seadoc.module.docdetail.di.DocDetailComponent
import com.coder.seadoc.module.docdetail.di.DocDetailModule
import com.coder.seadoc.module.docgroup.di.DocGroupComponent
import com.coder.seadoc.module.docgroup.di.DocGroupModule
import com.coder.seadoc.module.docgroup.di.DocListComponent
import com.coder.seadoc.module.docgroup.di.DocListModule
import com.coder.seadoc.module.newslist.di.NewsListComponent
import com.coder.seadoc.module.newslist.di.NewsListModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by liuting on 17/6/27.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, ApiModule::class))
interface AppComponent {
    fun plus(module: DocGroupModule): DocGroupComponent
    fun plus(module: DocListModule): DocListComponent
    fun plus(module: DocDetailModule): DocDetailComponent
    fun plus(module: NewsListModule): NewsListComponent
}