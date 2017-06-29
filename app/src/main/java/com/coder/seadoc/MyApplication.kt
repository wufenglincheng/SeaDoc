package com.coder.seadoc

import android.app.Application
import com.coder.seadoc.di.component.AppComponent
import com.coder.seadoc.di.component.DaggerAppComponent
import com.coder.seadoc.di.module.ApiModule
import com.coder.seadoc.di.module.AppModule

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
