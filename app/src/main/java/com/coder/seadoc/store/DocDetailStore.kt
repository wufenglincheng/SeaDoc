package com.coder.seadoc.store

import com.coder.seadoc.api.DocAPI
import com.coder.seadoc.model.*
import rx.Observable

/**
 * Created by liuting on 17/6/29.
 */
class DocDetailStore
constructor(private val api: DocAPI) {
    var getPageContent: GetPageContent? = null
    var blogPageList: ArrayList<BlogPage>? = null

    fun getMenu(moduleId: String): Observable<GetMenuContent> {
        val mapContent = HashMap<String, String>()
        mapContent.put("moduleId", moduleId)
        return api.getPageMenuByPageUrl(mapContent)
    }

    fun getPageData(url: String): Observable<ArrayList<BlogPage>> {
        val mapContent = HashMap<String, String>()
        mapContent.put("pageUrl", url)
        return api.getPageContent(mapContent)
                .doOnNext {
                    obj ->
                    getPageContent = obj
                }
                .map {
                    obj ->
                    val parceTranslateResult = obj.parceTranslateResult
                    val blogPageDetail = BlogPageDetail()
                    val parceResult = obj.parceResult
                    if (parceTranslateResult != null) {
                        blogPageDetail.apply {
                            pageContent = parceTranslateResult!!.content
                            id = parceTranslateResult!!.id
                        }
                    } else {
                        blogPageDetail.apply {
                            pageContent = parceResult?.content
                            id = parceResult?.id
                        }
                    }
                    val blogPage = BlogPage().apply {
                        this.blogPageDetail = blogPageDetail
                    }

                    blogPageList = ArrayList<BlogPage>().apply {
                        add(blogPage)
                        addAll(obj.blogPageList)
                    }
                    blogPageList
                }
    }

    fun getBlogPageDetail(): BlogPageDetail? {
        if (blogPageList != null && blogPageList!!.size > 0) {
            return blogPageList!![0]?.blogPageDetail
        } else {
            return null
        }
    }

    fun getBlogPage(positon: Int): BlogPage? {
        return if (blogPageList != null) blogPageList!![positon] else null
    }

    fun getRelativeBlogContent(id: Int): Observable<GetBlogPageDetail> {
        val map = java.util.HashMap<String, String>()
        map.put("blogPageId", id.toString())
        return api.getRelativeBlogContent(map)
    }

    fun translate(id: Int?, bid: String, content: String): Observable<BaseType> {
        val mapContent = java.util.HashMap<String, String>()
        mapContent.put("id", id.toString())
        mapContent.put("bid", bid)
        mapContent.put("result", content)
        return api.translateByLableId(mapContent)
    }

    fun updataBlogPageContent(content: String) {
        if (blogPageList != null && blogPageList!!.size > 0) {
            blogPageList!![0]?.blogPageDetail?.pageContent = content
        }
    }

    fun loadBlogList(url: String): Observable<ArrayList<BlogListItem>> {
        val mapContent = HashMap<String, String>()
        mapContent.put("pageUrl", url)
        return api.getBlogList(mapContent)
                .flatMap {
                    obj ->
                    Observable.just(obj.blogPageList)
                }
    }
}