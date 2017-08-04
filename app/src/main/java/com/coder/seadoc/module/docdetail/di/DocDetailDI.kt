package com.coder.seadoc.module.docdetail.di

import com.coder.seadoc.module.docdetail.BlogDetailActivity
import com.coder.seadoc.module.docdetail.DocDetailActivity
import com.coder.seadoc.module.docdetail.core.DocDetailContract
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope

/**
 * Created by liuting on 17/6/28.
 */
@DocDetailScope
@Subcomponent(modules = arrayOf(DocDetailModule::class))
interface DocDetailComponent {
    fun inject(activity: DocDetailActivity)
    fun inject(fragment: BlogDetailActivity)
}

@Module
class DocDetailModule(private val mView: DocDetailContract.DetailView) {
    @Provides fun provideView() = mView
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DocDetailScope