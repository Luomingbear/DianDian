package xyz.toofun.diandian.uitl

import android.content.Context
import android.util.TypedValue


/**
 * dip 和px相互转换
 * Created by bear on 2017/9/30.
 */
object DipPxConversion {
    /**
     * dip转化px
     *
     * @param context
     * @param dip
     * @return
     */
    fun dipToPx(context: Context?, dip: Int): Int {
        if (null == context) {
            return 0
        }
        val scale = context!!.getResources().getDisplayMetrics().density
        return (dip * scale + 0.5f * if (dip >= 0) 1 else -1).toInt()
    }

    /**
     * px转化dip
     *
     * @param context
     * @param px
     * @return
     */
    fun pxToDip(context: Context?, px: Int): Int {
        if (null == context) {
            return 0
        }
        val scale = context.getResources().getDisplayMetrics().density
        return (px / scale + 0.5f * if (px >= 0) 1 else -1).toInt()
    }

    fun dip2px(context: Context, dip: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics()).toInt()
    }

    fun px2dip(context: Context, px: Float): Int {
        val resources = context.getResources()
        val metrics = resources.getDisplayMetrics()
        val dp = px / (metrics.densityDpi / 160f)
        return dp.toInt()

    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.getResources().getDisplayMetrics().scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.getResources().getDisplayMetrics().scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}