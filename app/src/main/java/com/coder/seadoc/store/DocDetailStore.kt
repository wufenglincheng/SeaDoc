package com.coder.seadoc.store

import com.coder.seadoc.api.DocAPI
import com.coder.seadoc.model.BlogPage
import com.coder.seadoc.model.BlogPageDetail
import com.coder.seadoc.model.GetMenuContent
import com.coder.seadoc.model.GetPageContent
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
        val mapContent = java.util.HashMap<String, String>()
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
}