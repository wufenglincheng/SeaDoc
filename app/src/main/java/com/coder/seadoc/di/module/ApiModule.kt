package com.coder.seadoc.di.module

import com.coder.seadoc.BuildConfig
import com.coder.seadoc.api.DocAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by liuting on 17/6/27.
 */
@Module
open class ApiModule {

    companion object {
        private val LOCAL_BASE_URL: String = "http://114.215.220.48:8080/mydoc/"
        // private val LOCAL_BASE_URL: String = "http://192.168.1.106:8080/mydoc/";

        //private val LOCAL_BASE_URL: String = "http://114.215.220.48:8080/mydoc/";
        //private val LOCAL_BASE_URL: String = "http://192.168.0.105:8080/mydoc/";
        //private val LOCAL_BASE_URL: String = "http://172.20.10.7:8080/mydoc/";
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.networkDebug === 1) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(loggingInterceptor)
            httpClient.connectTimeout(10, TimeUnit.SECONDS)
        }
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(LOCAL_BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideDocApi(retrofit: Retrofit): DocAPI = retrofit.create(DocAPI::class.java)
}