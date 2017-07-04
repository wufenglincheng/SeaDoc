package com.coder.seadoc.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.coder.seadoc.utils.dpToPx


/**
 * Created by liuting on 17/6/30.
 */
class TabView : LinearLayout {

    var mViewPager: ViewPager? = null

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    fun setImages(images: Array<Int>) {
        orientation = HORIZONTAL
        for (drawable in images) {
            addView(ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                val bitmap = BitmapFactory.decodeResource(context.resources, drawable)
//                val mBitmapWidth = bitmap.width
//                val mBitmapHeight = bitmap.height
//                val mArrayColorLengh = mBitmapWidth * mBitmapHeight
//                val mArrayColor = IntArray(mArrayColorLengh)
//                val count = 0
//                for (i in 0..mBitmapWidth / 2) {
//                    for (j in 0..mBitmapHeight - 1) {
//                        //获得Bitmap 图片中每一个点的color颜色值
//                        //将需要填充的颜色值如果不是
//                        //在这说明一下 如果color 是全透明 或者全黑 返回值为 0
//                        //getPixel()不带透明通道 getPixel32()才带透明部分 所以全透明是0x00000000
//                        //而不透明黑色是0xFF000000 如果不计算透明部分就都是0了
////                    val color = mBitmap.getPixel(j, i)
////                    //将颜色值存在一个数组中 方便后面修改
////                    if (color == oldColor) {
//                        bitmap.setPixel(j, i, Color.BLACK)  //将白色替换成透明色
////                    }
//                    }
//                }
                setImageBitmap(bitmap)
            }, LayoutParams(dpToPx(50, context), dpToPx(36, context)))
        }
    }

    fun setViewPager(viewPager: ViewPager) {
        mViewPager = viewPager
        mViewPager?.apply {
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    val image = getChildAt(position) as ImageView
                }

                override fun onPageSelected(position: Int) {
                }

            })
        }
    }

    class MyDrawable constructor(val mBitmap: Bitmap) : Drawable() {

        var mPaint: Paint? = null

        init {
            mPaint = Paint()
            mPaint!!.apply {
                style = Paint.Style.FILL
            }
        }

        override fun setAlpha(alpha: Int) {

        }

        override fun getOpacity(): Int {
            return PixelFormat.TRANSLUCENT
        }


        override fun setColorFilter(colorFilter: ColorFilter?) {

        }

        override fun draw(canvas: Canvas?) {
            val mBitmapWidth = mBitmap.width
            val mBitmapHeight = mBitmap.height
            val mArrayColorLengh = mBitmapWidth * mBitmapHeight
            val mArrayColor = IntArray(mArrayColorLengh)
            val count = 0
            for (i in 0..mBitmapWidth / 2) {
                for (j in 0..mBitmapHeight - 1) {
                    //获得Bitmap 图片中每一个点的color颜色值
                    //将需要填充的颜色值如果不是
                    //在这说明一下 如果color 是全透明 或者全黑 返回值为 0
                    //getPixel()不带透明通道 getPixel32()才带透明部分 所以全透明是0x00000000
                    //而不透明黑色是0xFF000000 如果不计算透明部分就都是0了
//                    val color = mBitmap.getPixel(j, i)
//                    //将颜色值存在一个数组中 方便后面修改
//                    if (color == oldColor) {
                    mBitmap.setPixel(j, i, Color.BLACK)  //将白色替换成透明色
//                    }
                }
            }
            canvas?.drawBitmap(mBitmap, 0f, 0f, mPaint)
        }

    }
}