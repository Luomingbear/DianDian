package xyz.toofun.diandian.widget

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

/**
 * 这只是一个简单的ImageView，可以存放Bitmap和Path等信息
 *
 * @author xmuSistone
 */
class DataImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : AppCompatImageView(context, attrs, defStyle) {

    var absolutePath: String? = null
    var descripte: String? = null //描述

    var bitmap: Bitmap? = null
}