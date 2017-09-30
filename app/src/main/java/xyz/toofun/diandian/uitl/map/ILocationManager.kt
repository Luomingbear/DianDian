package xyz.toofun.diandian.uitl.map

import android.content.Context
import android.util.Log

import com.amap.api.location.AMapLocation
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker

import xyz.toofun.diandian.uitl.SharePreferenceManager


/**
 * 位置服务的管家,单例
 * Created by bear on 2016/12/2.
 */

class ILocationManager protected constructor() : IMapManager.OnMarkerClickedListener, ILocationSever.OnLocationChangeListener {
    private val TAG = "ILocationManager"

    companion object {
        val instance: ILocationManager by lazy { ILocationManager() }
    }

    private var mAppContext: Context? = null //应用上下文

    private var mAMap: AMap? = null //地图视图

    private var mLocationSever: ILocationSever? = null //定位服务

    private var mMapManager: IMapManager? = null //地图视图管家，定位的数据在地图上更新

    private var mLocationQueryTool: ILocationQueryTool? = null //位置搜索工具

    private var mLatLng = LatLng(0.0, 0.0) //位置数据


    /**
     * 初始化
     *
     * @param appContext appContext
     */
    fun init(appContext: Context, aMap: AMap?): ILocationManager {
        if (aMap == null)
            return this
        //        if (this.mAppContext == null)
        this.mAppContext = appContext.applicationContext
        //        if (this.mAMap == null)
        this.mAMap = aMap
        //        if (this.mLocationQueryTool == null)
        this.mLocationQueryTool = ILocationQueryTool(appContext)

        //        if (mMapManager == null) {
        mMapManager = IMapManager(mAppContext!!, mAMap)
        mMapManager!!.init()
        mMapManager!!.setOnMarkerClickedListener(this)
        //        }


        move2CurrentPosition()

        return this
    }

    /**
     * 是否初始化了
     *
     * @return
     */
    val isInit: Boolean
        get() {
            if (this.mAppContext == null)
                return false
            if (this.mAMap == null)
                return false
            return if (this.mLocationQueryTool == null) false else true
        }


    /**
     * 开始定位
     */
    fun start() {
        Log.i(TAG, "page: 定位管家开始服务！！！")
        if (mAppContext == null || mAMap == null) {
            Log.e(TAG, "page: 定位启动失败！")
            return
        }
        if (mLocationSever == null)
            mLocationSever = ILocationSever(mAppContext)

        //先停止上一次的定位
        mLocationSever?.stop()

        //定位开始
        mLocationSever?.start()
        mLocationSever?.setOnLocationChange(this)
        //
        animate2CurrentPosition()

        //显示用户图标
        mMapManager?.showMyPositionIcon(SharePreferenceManager.getLatLngData(mAppContext!!))
    }

    /**
     * 结束定位
     */
    fun pause() {
        //清除定位服务
        if (mLocationSever != null) {
            mLocationSever?.stop()
        }
    }

    /**
     * 销毁定位
     */
    fun destroy() {
        if (mAMap != null)
            mAMap?.clear()
        //清除定位服务
        if (mLocationSever != null) {
            mLocationSever?.destroy()
            mLocationSever = null
        }

        if (mMapManager != null)
            mMapManager = null
    }

    /**
     * 动画地移动摄像机到当前的位置
     */
    fun animate2CurrentPosition() {
        if (mMapManager == null)
            return

        mMapManager?.animate2Position()
    }

    /**
     * 移动摄像机到当前的位置
     */
    fun move2CurrentPosition() {
        if (mMapManager == null)
            return

        mMapManager?.move2Position()
    }


    /**
     * 定位服务获取到位置时执行
     *
     * @param aMapLocation 位置数据
     */
    override fun onLocationChange(aMapLocation: AMapLocation?) {
        val latLng = LatLng(aMapLocation!!.latitude, aMapLocation.longitude)

        //如果与上一次的坐标一致则不做任何操作
        if (latLng == mLatLng)
            return
        mLatLng = latLng

        /**
         * 移动地图的显示
         */
        moveMap(latLng)

        /**
         * 设置用户的图标显示
         */
        mMapManager!!.showMyPositionIcon(latLng)

        //保存位置信息到本地
        saveLatlngPreference(latLng)

        //保存城市名
        SharePreferenceManager.saveCityName(mAppContext!!, aMapLocation.city)
    }

    /**
     * 移动地图到当前的位置
     */
    private fun moveMap(latLng: LatLng) {
        mMapManager!!.animate2Position(latLng)
    }

    /**
     * 保存最近的一次定位数据
     *
     * @param latLng
     */
    private fun saveLatlngPreference(latLng: LatLng) {
        if (latLng != SharePreferenceManager.getLatLngData(mAppContext!!))
            SharePreferenceManager.saveLatLng(mAppContext!!, latLng)
    }

    private var onLocationMarkerClickListener: OnLocationMarkerClickListener? = null

    fun setOnLocationMarkerClickListener(onLocationMarkerClickListener: OnLocationMarkerClickListener): ILocationManager {
        this.onLocationMarkerClickListener = onLocationMarkerClickListener
        return this
    }


    override fun OnClick(marker: Marker) {

    }

    /**
     * 标记点点击回调函数
     */
    interface OnLocationMarkerClickListener {
        fun OnMarkerClick(marker: Marker)
    }


}