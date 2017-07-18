package com.coder.seadoc.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.graphics.Palette
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.widget.Toast
import com.bumptech.glide.load.resource.gif.GifDrawable

/**
 * Created by liuting on 17/6/28.
 */
val IMAGE_URL: String = "IMAGE_URL"
val NEWS_ID: String = "NEWS_ID"
val IS_LIGHT = 0
val IS_DARK = 1
val LIGHTNESS_UNKNOWN = 2

fun dpToPx(dp: Int, context: Context): Int = (context.resources.displayMetrics.density * dp).toInt()

fun Activity.requestScreenWidth(): Int {
    return this.resources.displayMetrics.widthPixels
}

fun FragmentActivity.requestScreenWidth(): Int {
    return this.resources.displayMetrics.widthPixels
}

fun Fragment.requestScreenWidth(): Int {
    return this.resources.displayMetrics.widthPixels
}

fun Fragment.showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

fun getBitmap(drawable: Drawable?): Bitmap? {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    } else if (drawable is GifDrawable) {
        return drawable.firstFrame
    }
    return null
}

fun isDark(palette: Palette): Int {
    val mostPopulous = getMostPopulousSwatch(palette) ?: return LIGHTNESS_UNKNOWN
    return if (isDark(mostPopulous.hsl)) IS_DARK else IS_LIGHT
}

fun isDark(bitmap: Bitmap, backupPixelX: Int, backupPixelY: Int): Boolean {
    // first try palette with a small color quant size
    val palette = Palette.from(bitmap).maximumColorCount(3).generate()
    if (palette != null && palette.swatches.size > 0) {
        return isDark(palette) == IS_DARK
    } else {
        // if palette failed, then check the color of the specified pixel
        return isDark(bitmap.getPixel(backupPixelX, backupPixelY))
    }
}

/**
 * Convert to HSL & check that the lightness value
 */
fun isDark(@ColorInt color: Int): Boolean {
    val hsl = FloatArray(3)
    android.support.v4.graphics.ColorUtils.colorToHSL(color, hsl)
    return isDark(hsl)
}
/**
 * 计算当前的亮度值
 */
fun isDark(hsl: FloatArray): Boolean { // @Size(3)
    return hsl[2] < 0.5f
}

fun getMostPopulousSwatch(palette: Palette?): Palette.Swatch? {
    var mostPopulous: Palette.Swatch? = null
    if (palette != null) {
        for (swatch in palette.swatches) {
            if (mostPopulous == null || swatch.population > mostPopulous.population) {
                mostPopulous = swatch
            }
        }
    }
    return mostPopulous
}

@ColorInt fun scrimify(@ColorInt color: Int,
                       isDark: Boolean,
                       @FloatRange(from = 0.0, to = 1.0) lightnessMultiplier: Float): Int {
    var lightnessMultiplier = lightnessMultiplier
    val hsl = FloatArray(3)
    android.support.v4.graphics.ColorUtils.colorToHSL(color, hsl)

    if (!isDark) {
        lightnessMultiplier += 1f
    } else {
        lightnessMultiplier = 1f - lightnessMultiplier
    }

    hsl[2] = constrain(0f, 1f, hsl[2] * lightnessMultiplier)
    return android.support.v4.graphics.ColorUtils.HSLToColor(hsl)
}

fun constrain(min: Float, max: Float, v: Float): Float {
    return Math.max(min, Math.min(max, v))
}

fun setLightStatusBar(view: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = view.systemUiVisibility
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        view.systemUiVisibility = flags
    }
}

fun getFastOutSlowInInterpolator(context: Context): Interpolator {
    var fastOutSlowIn: Interpolator = AnimationUtils.loadInterpolator(context,
    android.R.interpolator.fast_out_slow_in)
    return fastOutSlowIn
}