package com.coder.seadoc.utils;

/**
 * Created by XL on 2017/6/27.
 */

import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class MyObject {
    private Handler handler = null;
    private WebView webView = null;

    public MyObject(WebView webView, Handler handler) {
        this.webView = webView;
        this.handler = handler;
    }

    @JavascriptInterface
    public void englishOnly() {
        //通过handler来确保init方法的执行在主线程中
        handler.post(new Runnable() {

            public void run() {
                //调用客户端setContactInfo方法
                webView.loadUrl("javascript:englishOnly()");
            }
        });
    }

    @JavascriptInterface
    public void chineseOnly() {
        //通过handler来确保init方法的执行在主线程中
        handler.post(new Runnable() {

            public void run() {
                //调用客户端setContactInfo方法
                webView.loadUrl("javascript:chineseOnly()");
            }
        });
    }

    @JavascriptInterface
    public void showAll() {
        //通过handler来确保init方法的执行在主线程中
        handler.post(new Runnable() {

            public void run() {
                //调用客户端setContactInfo方法
                webView.loadUrl("javascript:showAll()");
            }
        });
    }
}
