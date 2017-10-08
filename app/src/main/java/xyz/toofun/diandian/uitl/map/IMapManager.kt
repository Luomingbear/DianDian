package xyz.toofun.diandian.uitl.map

import android.content.Context
import android.util.Log
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import xyz.toofun.diandian.uitl.NameUtil
import xyz.toofun.diandian.uitl.SharePreferenceManager
import xyz.toofun.diandian.widget.marker.MyCircleMarker
import java.io.File


/**
 * mapview的管理者
 * Created by bear on 2016/12/2.
 */

class IMapManager {
    private lateinit var mContext: Context
    private lateinit var mAMap: AMap //地图对象
    private var mLatLng: LatLng? = null //当前位置经纬度
    private lateinit var mUiSettings: UiSettings //地图ui设置
    private val isFirstZoom = true //是否首次启动地图

    private val mZoomLevel = 15 //默认的地图缩放比例
    private val markerMinDistance = 15 //图标之间的最小距离，小于这个距离就只会显示一个！


    private var mMyPositionMarker: MyCircleMarker? = null //个人位置点图标

    constructor(context: Context, aMap: AMap?) {
        if (aMap == null)
            return
        mContext = context.applicationContext
        this.mAMap = aMap
        mUiSettings = mAMap.uiSettings
    }

    /**
     * 设置夜间模式
     */
    fun setNightMode() {
        mAMap.mapType = AMap.MAP_TYPE_NIGHT
    }

    /**
     * 设置日间模式
     */
    fun setDayMode() {
        mAMap.mapType = AMap.MAP_TYPE_NORMAL
    }

    /**
     * 初始化地图
     */
    fun init() {
        if (mAMap == null)
            return

        //        if (ISharePreference.isNightMode(mContext))
        //            setNightMode();
        //        else setDayMode();

        /**
         * 显示地图
         */
        //获得最近一次的位置数据
        mLatLng = SharePreferenceManager.getLatLngData(mContext)
        if (mLatLng == null)
            return

        /**
         * 点击事件
         */
        mAMap.setOnMarkerClickListener(onMarkerClickListener)

        /**
         * ui
         */
        setUI()

    }


    /**
     * 设置地图上面的ui显示，仅仅是对原始的地图控件的ui进行改变
     */
    private fun setUI() {
        if (mUiSettings == null)
            return

        //设置自定义的地图
        val path = mContext.filesDir.absolutePath + File.separator + NameUtil.MAP_STYLE

        val file = File(path)
        Log.i(TAG, "setUI: File:" + file.exists())

        mAMap.setCustomMapStylePath(path)
        mAMap.setMapCustomEnable(true)

        //不显示建筑物
        mAMap.showBuildings(false)

        //不显示文字
        //        mAMap.showMapText(false);

        //限制显示的范围
        //        LatLng southwestLatLng = new LatLng(mLatLng.latitude - 0.01, mLatLng.longitude - 0.01);
        //        LatLng northeastLatLng = new LatLng(mLatLng.latitude + 0.01, mLatLng.longitude + 0.01);
        //        LatLngBounds latLngBounds = new LatLngBounds(southwestLatLng, northeastLatLng);
        //        mAMap.setMapStatusLimits(latLngBounds);

        //隐藏缩放按钮
        mUiSettings.isZoomControlsEnabled = false
        //旋转
        mUiSettings.isRotateGesturesEnabled = false
        //缩放
        //        mUiSettings.setZoomGesturesEnabled(false);
        //倾斜
        mUiSettings.isTiltGesturesEnabled = false
        //移动
        //        mUiSettings.setScrollGesturesEnabled(false);
        //
        //
        mUiSettings.isIndoorSwitchEnabled = false

    }


    private val onMarkerClickListener = AMap.OnMarkerClickListener { marker ->
        if (onMarkerClickedListener != null)
            onMarkerClickedListener!!.OnClick(marker)
        false
    }

    /**
     * 显示用户自己位置的图标
     */
    fun showMyPositionIcon(latLng: LatLng?) {
        if (latLng == null)
            return
        //        Log.i(TAG, "showMyPositionIcon: !!!!!!!!!!!!!!!");
        if (mMyPositionMarker == null)
            mMyPositionMarker = MyCircleMarker(mContext, mAMap, latLng)

        if (AMapUtils.calculateLineDistance(mMyPositionMarker?.latLng, mLatLng) > 200) {
            mAMap.clear()
            mMyPositionMarker = MyCircleMarker(mContext, mAMap, latLng)
        } else
            mMyPositionMarker?.animate(latLng)

    }

    /**
     * 移动地图到当前位置
     */
    fun animate2Position() {
        if (mAMap == null)
            return

        if (mLatLng == null) {
            mLatLng = SharePreferenceManager.getLatLngData(mContext)
        }
        if (mLatLng != null)
            animate2Position(mLatLng!!)
    }

    /**
     * 移动到制定位置
     *
     * @param latLng
     */
    fun animate2Position(latLng: LatLng) {
        if (mAMap == null)
            return
        //        mLatLng = latLng;
        mAMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition(latLng,
                        mZoomLevel.toFloat(), //新的缩放级别
                        0f, //俯仰角0°~45°（垂直与地图时为0）
                        0f)), //偏航角 0~360° (正北方为0)
                750, //移动时间 豪秒
                object : AMap.CancelableCallback {
                    override fun onFinish() {

                        //移动完成
                    }

                    override fun onCancel() {

                        //移动取消
                    }
                })
    }

    /**
     * 移动到制定位置
     *
     * @param latLng
     */
    fun animate2Position(latLng: LatLng, time: Int) {
        if (mAMap == null)
            return
        //        mLatLng = latLng;
        mAMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition(latLng,
                        mZoomLevel.toFloat(), //新的缩放级别
                        0f, //俯仰角0°~45°（垂直与地图时为0）
                        0f)), //偏航角 0~360° (正北方为0)
                time.toLong(), //移动时间 豪秒
                object : AMap.CancelableCallback {
                    override fun onFinish() {

                        //移动完成
                    }

                    override fun onCancel() {

                        //移动取消
                    }
                })
    }

    /**
     * 马上移动摄像机到指定位置
     *
     * @param latLng
     */
    @JvmOverloads
    fun move2Position(latLng: LatLng? = mLatLng) {
        if (mAMap == null)
            return
        //        mLatLng = latLng;
        mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(
                latLng,
                mZoomLevel.toFloat(),
                0f,
                0f
        )))
    }

    private var onMarkerClickedListener: OnMarkerClickedListener? = null

    fun setOnMarkerClickedListener(onMarkerClickListener: OnMarkerClickedListener) {
        this.onMarkerClickedListener = onMarkerClickListener
    }

    /**
     * 标记点 点击回调
     */
    interface OnMarkerClickedListener {
        fun OnClick(marker: Marker)
    }

    companion object {
        private val TAG = "IMapManager"
    }
}