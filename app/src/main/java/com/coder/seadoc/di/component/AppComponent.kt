package com.coder.seadoc.di.component

import com.coder.seadoc.di.*
import com.coder.seadoc.di.module.ApiModule
import com.coder.seadoc.di.module.AppModule
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
}