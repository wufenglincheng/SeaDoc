package com.coder.seadoc.module.docgroup.di

import com.coder.seadoc.module.docgroup.core.DocGroupContract
import com.coder.seadoc.module.docgroup.core.DocListContract
import com.coder.seadoc.module.docgroup.DocGroupFragment
import com.coder.seadoc.module.docgroup.DocListFragment
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
class DocGroupModule(private val mView: DocGroupContract.View) {
    @Provides fun provideView() = mView
}

@Subcomponent(modules = arrayOf(DocListModule::class))
interface DocListComponent {
    fun inject(fragment: DocListFragment)
}

@Module
class DocListModule(private val mView: DocListContract.View) {
    @Provides fun provideView() = mView
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DocScope