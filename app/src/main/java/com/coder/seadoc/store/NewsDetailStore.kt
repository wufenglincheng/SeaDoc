package com.coder.seadoc.store

import com.coder.seadoc.api.DocAPI
import rx.Observable

/**
 * Created by liuting on 17/7/7.
 */
class NewsDetailStore constructor(val mApi: DocAPI) {
    fun load(id: Int): Observable<String> {
        val mapContent = HashMap<String, String>()
        mapContent.put("id", id.toString())
       return mApi.getNewsDetail(mapContent)
                .flatMap {
                    obj ->
                    Observable.just(obj.content)
                }
    }

}