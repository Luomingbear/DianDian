package xyz.toofun.diandian.widget.marker

import android.content.Context

import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker


/**
 * 地图上的标记图标的基础类
 * Created by bear on 2016/12/2.
 */

open class IMarker {
    var mMarker: Marker? = null //标记对象
    lateinit var mContext: Context
    lateinit var mAMap: AMap //地图对象
    lateinit var mLatLng: LatLng //经纬度

    protected constructor() {}

    constructor(mContext: Context, mAMap: AMap, mLatLng: LatLng) {
        this.mContext = mContext
        this.mAMap = mAMap
        this.mLatLng = mLatLng
    }

    var latLng: LatLng
        get() = mLatLng
        set(mLatLng) {
            this.mLatLng = mLatLng
            this.mMarker!!.position = mLatLng
        }

    /**
     * 清除标记,会留下疤痕
     */
    fun destroy() {
        if (mMarker != null)
            mMarker!!.destroy()
    }

    fun remove() {
        if (mMarker != null)
            mMarker!!.remove()
    }
}
