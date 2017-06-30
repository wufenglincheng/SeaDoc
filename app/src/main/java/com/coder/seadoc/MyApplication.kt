package com.coder.seadoc

import android.app.Application
import com.coder.seadoc.base.di.AppComponent
import com.coder.seadoc.base.di.ApiModule
import com.coder.seadoc.base.di.AppModule
import com.coder.seadoc.base.di.DaggerAppComponent

/**
 * Created by liuting on 17/6/27.
 */
class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        ResUtil.inject(this)
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule())
                .build()
    }
}
