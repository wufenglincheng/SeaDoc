package com.coder.seadoc.utils

import android.content.res.AssetManager
import android.webkit.WebResourceResponse
import android.webkit.WebViewClient

/**
 * Created by liuting on 17/8/2.
 */
fun WebViewClient.localUri(response: WebResourceResponse?, url: String?, assets: AssetManager): WebResourceResponse? {
    when (url) {
        "https://cdn-static-1.medium.com/_/fp/css/fonts-lazy-latin-base.jMU532QDmysQMOINr-cr2A.css" -> {
            return WebResourceResponse("text/css", "UTF-8", assets.open("newsCss_one.css"))
        }
        "https://cdn-static-1.medium.com/_/fp/css/main-base.tATn6NpWuPoMEq2rVxpt0A.css" -> {
            return WebResourceResponse("text/css", "UTF-8", assets.open("newsCss_two.css"))
        }
        "http://114.215.220.48:8080/mydoc/pages/assets/docker/css/temp.css" -> {
            return WebResourceResponse("text/css", "UTF-8", assets.open("temp.css"))
        }
        "http://114.215.220.48:8080/mydoc/pages/assets/docker/css/family.css" -> {
            return WebResourceResponse("text/css", "UTF-8", assets.open("family.css"))
        }
        "http://114.215.220.48:8080/mydoc/pages/assets/docker/css/style.css" -> {
            return WebResourceResponse("text/css", "UTF-8", assets.open("style.css"))
        }
        "http://114.215.220.48:8080/mydoc/pages/assets/docker/css/perldoc.css" -> {
            return WebResourceResponse("text/css", "UTF-8", assets.open("perldoc.css"))
        }
        "http://114.215.220.48:8080/mydoc/pages/assets/bower_components/font-awesome/css/font-awesome.min.css" -> {
            return WebResourceResponse("text/css", "UTF-8", assets.open("font-awesome.min.css"))
        }
        "http://114.215.220.48:8080/mydoc/pages/assets/bower_components/bootstrap/dist/css/bootstrap.min.css" -> {
            return WebResourceResponse("text/css", "UTF-8", assets.open("bootstrap.min.css"))
        }
        "http://114.215.220.48:8080/mydoc/pages/assets/docker/js/bootstrap.min.js" -> {
            return WebResourceResponse("application/x-javascript", "UTF-8", assets.open("bootstrap.min.js"))
        }
        else -> return response
    }
}