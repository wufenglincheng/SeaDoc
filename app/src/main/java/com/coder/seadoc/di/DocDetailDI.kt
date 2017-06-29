package com.coder.seadoc.di

import com.coder.seadoc.api.DocAPI
import com.coder.seadoc.presenter.attacher.DocDetailAttacher
import com.coder.seadoc.store.DocDetailStore
import com.coder.seadoc.ui.activity.DocDetailActivity
import com.coder.seadoc.ui.fragment.DocLeftFragment
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
    fun inject(fragment: DocLeftFragment)
}

@Module
class DocDetailModule(private val mView: DocDetailAttacher.DetailView) {
    @Provides fun provideView() = mView
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DocDetailScope