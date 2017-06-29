package com.coder.seadoc.di

import com.coder.seadoc.presenter.attacher.DocGroupAttacher
import com.coder.seadoc.presenter.attacher.DocListAttacher
import com.coder.seadoc.ui.fragment.DocGroupFragment
import com.coder.seadoc.ui.fragment.DocListFragment
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope

/**
 * Created by liuting on 17/6/27.
 */
@Subcomponent(modules = arrayOf(DocGroupModule::class))
interface DocGroupComponent {
    fun inject(fragment: DocGroupFragment)
}

@Module
class DocGroupModule(private val mView: DocGroupAttacher.View) {
    @Provides fun provideView() = mView
}

@Subcomponent(modules = arrayOf(DocListModule::class))
interface DocListComponent {
    fun inject(fragment: DocListFragment)
}

@Module
class DocListModule(private val mView: DocListAttacher.View) {
    @Provides fun provideView() = mView
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DocScope