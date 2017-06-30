package com.coder.seadoc.model

import java.io.Serializable
import java.util.*

/**
 * Created by liuting on 17/6/29.
 */
data class GetMenuContent(var errCode: Int?,
                          var errMsg: String?,
                          var count: Int?,
                          var entryMenu: EntryMenu?,
                          var entryMenuFinal: EntryMenuFinal?)

data class EntryMenu(var projectModule: ProjectModule?,
                     var id: Int?,
                     var moduleId: Int?,
                     var menuContent: String?)

data class EntryMenuFinal(var id: Int?,
                          var menuId: Int?,
                          var moduleId: Int?,
                          var content: String?)

data class GetPageContent(var errCode: Int = 0,
                          var errMsg: String?,
                          var count: Int = 0,
                          var parceResult: ParceResult?,
                          var parceTranslateResult: ParceTranslateResult?,
                          var blogPageList: List<BlogPage> = ArrayList<BlogPage>())

data class ParceResult(var id: Int? = 0,
                       var menuItemId: Int? = 0,
                       var content: String?)

data class ParceTranslateResult(var id: Int = 0,
                                var menuItemId: Int = 0,
                                var content: String? = null)

data class BlogPage(var blogPageDetail: BlogPageDetail? = null,
                    var id: Int = 0,
                    var projectName: String? = null,
                    var blogPageUrl: String? = null,
                    var blogTitle: String? = null,
                    var blogTag: String? = null,
                    var blogTime: String? = null,
                    var blogClick: String? = null,
                    var blogAuthor: String? = null,
                    var blogLogoUrl: String? = null,
                    var menuItemId: Int? = null,
                    private var bPageUrl: String? = null) : Serializable

data class BlogPageDetail(var id: Int? = null,
                          var blogPageId: Int? = null,
                          var pageContent: String? = null) : Serializable

class GetBlogPageDetail(var blogPageDetail: BlogPageDetail? = null,
                        errCode: Int?,
                        errMsg: String?) : BaseType(errCode, errMsg)