package xyz.toofun.diandian.uitl

import android.graphics.Bitmap
import android.view.View

/**
 * view转化为bitmap
 * Created by bear on 2016/12/2.
 */

object ViewBitmapTool {
    //布局 转bitmap
    fun convertLayoutToBitmap(view: View): Bitmap {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache(true)
        return view.drawingCache
    }

}
