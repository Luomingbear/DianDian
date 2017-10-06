package xyz.toofun.diandian.widget.marker

import android.content.Context
import android.view.animation.LinearInterpolator
import com.amap.api.maps.AMap
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.animation.TranslateAnimation
import xyz.toofun.diandian.uitl.ViewBitmapTool

/**
 * 用户个人位置的圆点图标
 * Created by bear on 2017/3/14.
 */

class MyCircleMarker(mContext: Context, mAMap: AMap, mLatLng: LatLng) : IMarker(mContext, mAMap, mLatLng) {
    private lateinit var mMyCircleView: MyCircleView

    init {
        init()
    }

    private fun init() {
        mMyCircleView = MyCircleView(mContext)
        val bitmap = ViewBitmapTool.convertLayoutToBitmap(mMyCircleView)

        val markerOptions = MarkerOptions()
        markerOptions.position(mLatLng)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap))

        mMarker = mAMap.addMarker(markerOptions)
    }

    /**
     * 动画地移动marker
     *
     * @param latLng
     */
    fun animate(latLng: LatLng) {
        val animation = TranslateAnimation(latLng)
        animation.setInterpolator(LinearInterpolator())
        animation.setDuration(2000)

        mMarker!!.isClickable = false
        mMarker!!.isInfoWindowEnable = false
        mMarker!!.setAnimation(animation)
        mMarker!!.startAnimation()
    }

    /**
     * 移动marker
     *
     * @param latLng
     */
    fun move(latLng: LatLng) {
        val animation = TranslateAnimation(latLng)
        animation.setInterpolator(LinearInterpolator())

        mMarker!!.setAnimation(animation)
        mMarker!!.startAnimation()
    }
}
