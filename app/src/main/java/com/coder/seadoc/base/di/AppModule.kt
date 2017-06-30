package com.coder.seadoc.base.di

import android.content.Context
import android.content.res.Resources
import com.coder.seadoc.api.DocAPI
import com.coder.seadoc.store.DocDetailStore
import com.coder.seadoc.store.DocGroupStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by liuting on 17/6/27.
 */
@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideResources(context: Context): Resources {
        return context.resources
    }

    @Provides
    @Singleton
    fun provideDocStore(api : DocAPI): DocGroupStore {
        return DocGroupStore(api)
    }

    @Provides @Singleton fun provideStore(api: DocAPI): DocDetailStore = DocDetailStore(api)
}