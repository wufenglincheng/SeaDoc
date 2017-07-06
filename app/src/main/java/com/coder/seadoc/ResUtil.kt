package com.coder.seadoc

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by liuting on 17/6/27.
 */
class ResUtil private constructor(val context: Context) {

    private val mDisplayMetrics: DisplayMetrics = context.resources.displayMetrics
    private val formatMap = HashMap<String, SimpleDateFormat>()
    private val createAtCalendar = Calendar.getInstance()
    private val currentCalendar = Calendar.getInstance()
    fun getDrawable(id: Int): Drawable {
        return context.resources.getDrawable(id)
    }

    fun getScreenWidth(): Int = mDisplayMetrics.widthPixels

    fun getScreenHeight(): Int = mDisplayMetrics.heightPixels

    fun toDateText(writeTime: Long): String? {
        val date :Date = Date(writeTime)
        var string = ""
        val currentDate = Date()
        if (date != null) {
            val createTime = date.time
            val currentTime = currentDate.time
            val time = currentTime - createTime
            val minute = Math.floor((time / MINUTE).toDouble()).toInt()
            if (minute < 1) {
                string = context.getString(R.string.just_now)
            } else if (minute < 60) {
                val minsAgo = context.getString(R.string.the_before_minute)
                string = minute.toString() + " " + minsAgo
            } else {
                val createStr = getSimpleDataFormat(DATE_FORMAT_DEFAULT_ZONE).format(date)
                createAtCalendar.set(date.year, date.month, date.date, 0, 0, 0)
                currentCalendar.set(currentDate.year, currentDate.month, currentDate.date, 0, 0, 0)
                val createDay = createAtCalendar.time.time
                val currentDay = currentCalendar.time.time
                val dayTime = currentDay - createDay
                val day = Math.floor((dayTime / DAY).toDouble()).toInt()
                if (day == 0) {
                    val today = context.getString(R.string.today)
                    string = today + " " + createStr.substring(9)

                } else if (day == 1) {
                    val yesterday = context.getString(R.string.yesterday)
                    string = yesterday + " " + createStr.substring(9)
                } else {
                    string = createStr.substring(0, 8)

                }
            }
        }
        return string
    }

    private fun getSimpleDataFormat(format: String): SimpleDateFormat {
        var sdf: SimpleDateFormat? = formatMap[format]
        if (sdf == null) {
            if (SIMPLE_DATE_FORMAT.contentEquals(format)) {
                sdf = SimpleDateFormat(format, Locale.ENGLISH)
                sdf.timeZone = TimeZone.getTimeZone("GMT")
            } else {
                sdf = SimpleDateFormat(format)
            }
            formatMap.put(format, sdf)
        }
        return sdf
    }
    companion object {
        /**
         * 一分钟换算的毫秒数
         */
        private val MINUTE = 60000L
        /**
         * 一天换算的毫秒数
         */
        private val DAY = 86400000L
        /**
         * 一月换算的毫秒数
         */
        private val MONTH = DAY * 30
        /**
         * 一年换算的毫秒数
         */
        private val YEAR = MONTH * 365
        /**
         * Simple date format
         */
        val SIMPLE_DATE_FORMAT = "EEE MMM dd HH:mm:ss z yyyy"//"EEE MMM dd HH:mm:ss z yyyy"
        /**
         * Simple date format
         */
        val DATE_FORMAT_DEFAULT_ZONE = "yy-MM-dd HH:mm"
        var instance: ResUtil? = null
        fun inject(context: Context) {
            if (instance == null) {
                instance = ResUtil(context)
            }
        }
    }
}