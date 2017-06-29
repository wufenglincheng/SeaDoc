package com.coder.seadoc.store

import com.coder.seadoc.api.DocAPI
import com.coder.seadoc.model.GetProjectGroupList
import rx.Observable

/**
 * Created by liuting on 17/6/27.
 */
class DocGroupStore
constructor(val api: DocAPI) {
    var docGroup: GetProjectGroupList? = null

    /**
     * 加载数据 如果本地有数据则返回本地，没有则请求网路
     */
    fun load(): Observable<GetProjectGroupList> {
        return loadFromCache()
                .flatMap {
                    obj ->
                    if (obj == null)
                        loadFromNet()
                    else
                        loadFromCache()
                }
                .doOnNext {
                    obj ->
                    docGroup = obj
                }
    }

    //从缓存获取
    fun loadFromCache(): Observable<GetProjectGroupList> = Observable.fromCallable { docGroup }

    //从网络获取
    fun loadFromNet(): Observable<GetProjectGroupList> = api.getProjectGroupList()
}