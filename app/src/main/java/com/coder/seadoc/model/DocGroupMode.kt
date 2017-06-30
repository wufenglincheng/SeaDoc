package com.coder.seadoc.model

import java.io.Serializable

/**
 * Created by liuting on 17/6/27.
 */
class GetProjectGroupList(var count: Int?,
                          var classifyList: ArrayList<Classify>?,
                          errCode: Int?,
                          errMsg: String?) : BaseType(errCode, errMsg)

data class Classify(var projects: ArrayList<Project>?,
                    var id: Int = 0,
                    var name: String) : Serializable

data class Project(var projectModuleList: ArrayList<ProjectModule>?,
                   var id: Int?,
                   var projectName: String?,
                   var classifyId: Int?,
                   var baseUrl: String?) : Serializable

data class ProjectModule(var id: Int?,
                         var projectId: Int?,
                         var entryUrl: String?,
                         var moduleName: String?,
                         var describtion: String?,
                         var displayName: String?) : Serializable