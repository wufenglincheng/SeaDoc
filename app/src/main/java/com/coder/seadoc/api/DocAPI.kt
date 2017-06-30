package com.coder.seadoc.api

import com.coder.seadoc.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import rx.Observable

/**
 * Created by liuting on 17/6/27.
 */
open interface DocAPI {
    companion object {
        const val API_LOCAL_TEST = "test.json"
        const val API_GET_PROJECTGROUPLIST = "m_getprojectGroupList.do"
        const val API_GET_PAGE_CONTENT = "m_getPageContentByPageUrl.do"
        const val API_GET_PAGE_MENU = "m_getPageMenuContent.do"
        const val API_GET_RELATIVE_BLOG = "m_getRelativeBlogList.do"
        const val API_GET_RELATIVE_BLOG_CONTENT = "m_getRelativeBlogDetail.do"
        const val API_GET_RELATIVE_MOST_BLOG = "m_getMostClickBlogDetail.do"
        const val API_GET_PAGE_MENU_BY_PAGEURL = "m_getPageMenuByPageUrl.do"
        const val API_TRANSLATE_BY_LABEL_ID = "m_translateByLableId.do"
    }

    @GET(API_GET_PROJECTGROUPLIST)
    fun getProjectGroupList(): Observable<GetProjectGroupList>

    @GET(API_GET_PAGE_MENU_BY_PAGEURL)
    fun getPageMenuByPageUrl(@QueryMap map: Map<String, String>): Observable<GetMenuContent>

    @GET(API_GET_PAGE_CONTENT)
    fun getPageContent(@QueryMap map: Map<String, String>): Observable<GetPageContent>

    @GET(API_GET_RELATIVE_BLOG_CONTENT)
    fun getRelativeBlogContent(@QueryMap map: Map<String, String>): Observable<GetBlogPageDetail>

    @GET(API_TRANSLATE_BY_LABEL_ID)
    fun translateByLableId(@QueryMap map: Map<String, String>): Observable<BaseType>
}