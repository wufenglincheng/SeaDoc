package com.coder.seadoc.module.newsdetail

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.LightingColorFilter
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.webkit.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.coder.seadoc.R
import com.coder.seadoc.base.BaseActivity
import com.coder.seadoc.module.newsdetail.core.NewsDetailContract
import com.coder.seadoc.module.newsdetail.core.NewsDetailPresenter
import com.coder.seadoc.module.newsdetail.di.NewsDetailModule
import com.coder.seadoc.utils.*
import kotlinx.android.synthetic.main.activity_news_detail.*
import javax.inject.Inject

/**
 * Created by liuting on 17/7/7.
 */
class NewsDetailActivity : NewsDetailContract.View, BaseActivity() {
    private val SCRIM_ADJUSTMENT = 0.075f
    var imageUrl: String? = null
    @Inject
    lateinit var mPresenter: NewsDetailPresenter

    var backDrawable: Drawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        requestComponent().plus(NewsDetailModule(this)).inject(this)
        backDrawable = resources.getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha).mutate()
        setUpToolbar("", backDrawable!!)
        imageUrl = intent.getStringExtra(IMAGE_URL)
        cover.apply {
            layoutParams.height = (requestScreenWidth() * 9f / 16).toInt()
            layoutParams = layoutParams
        }
        Glide.with(this)
                .load(imageUrl)
                .listener(coverLoadListener)
                .apply(RequestOptions().centerCrop())
                .into(cover)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            runEnterTransition()
        } else {
            webViewParent.alpha = 1f
        }
        initData()
        initListener()
    }

    private fun initListener() {
        appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val scrollRange = appBarLayout.totalScrollRange
            val percent = Math.abs(verticalOffset).toFloat() / scrollRange
            //延迟alpha生效，防止阻塞页面加载
            cover.post({ cover.setForegroundAlpha(percent) })
        }
    }

    fun initData() {
        webView.apply {
//            settings.setAppCacheEnabled(true)
//            settings.domStorageEnabled = true
//            settings.databaseEnabled = true
//            settings.cacheMode = WebSettings.LOAD_DEFAULT
//            settings.javaScriptEnabled = true
//            settings.loadWithOverviewMode = true
//            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
//            settings.setSupportZoom(true)
            setWebViewClient(object : WebViewClient() {
                override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
                    var response = super.shouldInterceptRequest(view, url)
                    if (url.isNullOrBlank()) return response
                    if (url.equals("https://cdn-static-1.medium.com/_/fp/css/fonts-lazy-latin-base.jMU532QDmysQMOINr-cr2A.css")) {
                        response = WebResourceResponse("text/css", "UTF-8", assets.open("newsCss_one.css"))
                    }
                    if (url.equals("https://cdn-static-1.medium.com/_/fp/css/main-base.tATn6NpWuPoMEq2rVxpt0A.css")) {
                        response = WebResourceResponse("text/css", "UTF-8", assets.open("newsCss_two.css"))
                    }
                    return response
                }
            })
            setWebChromeClient(WebChromeClient())
        }
        mPresenter.load(intent.getIntExtra(NEWS_ID, -1))
    }

    @RequiresApi(21)
    private fun runEnterTransition() {
        window.sharedElementsUseOverlay = true
        postponeEnterTransition()
        cover.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                cover.viewTreeObserver.removeOnPreDrawListener(this)
                startPostponedEnterTransition()
                return true
            }
        })

    }

    override fun setPageData(content: CharSequence) {
        webView.stopLoading()
        webView.loadDataWithBaseURL(null, content.toString(), "text/html", "utf-8", null)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webViewParent.visibility = View.GONE
            finishAfterTransition()
        } else {
            finish()
        }
    }


    private var coverLoadListener = object : RequestListener<Drawable> {
        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            var bitmap = getBitmap(resource)
            //设置前色
            val result = Blur.GaussianBlur(this@NewsDetailActivity, Bitmap.createScaledBitmap(bitmap, requestScreenWidth() / 15, dpToPx(275, this@NewsDetailActivity) / 15, false), 25)
            cover.foreground = BitmapDrawable(result)
            //设置按钮颜色
            val twentyFourDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    24f, this@NewsDetailActivity.resources.displayMetrics).toInt()
            if (bitmap == null) return false
            Palette.from(bitmap)
                    .maximumColorCount(3)
                    .clearFilters()
                    .setRegion(0, 0, bitmap.width - 1, twentyFourDip)
                    .generate { palette ->
                        var isDark: Boolean
                        val lightness = isDark(palette)
                        if (lightness == LIGHTNESS_UNKNOWN) {
                            isDark = isDark(bitmap, bitmap.width / 2, 0)
                        } else {
                            isDark = lightness == IS_DARK
                        }
                        if (!isDark) { // 设置在亮的图片上显示暗的back按钮
                            backDrawable?.colorFilter = LightingColorFilter(0, ContextCompat.getColor(
                                    this@NewsDetailActivity, R.color.dark_icon))
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //statusBar 颜色设置
                            var statusBarColor = window.statusBarColor
                            val topColor = getMostPopulousSwatch(palette)
                            if (topColor != null && (isDark || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                                statusBarColor = scrimify(topColor.rgb,
                                        isDark, SCRIM_ADJUSTMENT)
                                if (!isDark && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    setLightStatusBar(cover)
                                }
                            }
                            if (statusBarColor != window.statusBarColor) {
                                val statusBarColorAnim = ValueAnimator.ofArgb(
                                        window.statusBarColor, statusBarColor)
                                statusBarColorAnim.addUpdateListener { animation -> window.statusBarColor = animation.animatedValue as Int }
                                statusBarColorAnim.duration = 1000L
                                statusBarColorAnim.interpolator = getFastOutSlowInInterpolator(this@NewsDetailActivity)
                                statusBarColorAnim.start()
                            }
                        }

                    }
            return false
        }

        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            return false
        }
    }
}