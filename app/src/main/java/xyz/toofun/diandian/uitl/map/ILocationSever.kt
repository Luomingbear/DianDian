package xyz.toofun.diandian.uitl.map

import android.content.Context
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import java.util.*

/**
 * 具体实现定位服务的类
 * Created by bear on 2016/11/23.
 */

class ILocationSever(applicationContext: Context?) {
    //声明AMapLocationClient类对象
    var mLocationClient: AMapLocationClient? = null
    //声明AMapLocationClientOption对象
    var mLocationOption: AMapLocationClientOption? = null
    private val mIntervalTime = 2000 //定位间隔时间毫秒
    private var mTimer: Timer? = null //用于重新定位服务

    init {
        //初始化定位
        mLocationClient = AMapLocationClient(applicationContext)
    }

    //声明定位回调监听器
    var mLocationListener: AMapLocationListener = AMapLocationListener { aMapLocation ->
        if (aMapLocation != null) {
            if (aMapLocation.errorCode == 0) {

                //可在其中解析amapLocation获取相应内容。
                if (onLocationChange != null)
                    onLocationChange!!.onLocationChange(aMapLocation)
//                Log.i(TAG, "onLocationChanged: " + aMapLocation);

                mTimer?.cancel()
                mTimer = null

            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.errorCode + ", errInfo:"
                        + aMapLocation.errorInfo)

                if (aMapLocation.errorCode == 12) {
                    //缺少定位权限
                    return@AMapLocationListener
                }else{

                }
            }
        }
    }

    /**
     * 初始化
     * 设置定位间隔时间精确度等
     */
    private fun init() {
        //设置定位回调监听
        mLocationClient!!.setLocationListener(mLocationListener)
        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption()

        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption!!.isMockEnable = false

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy

        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption!!.interval = mIntervalTime.toLong()

        /**
         * 获取最近3s内的最高精度定位
         */
        //        //获取一次定位结果：
        //        //该方法默认为false。
        //        mLocationOption.setOnceLocation(false);
        //
        //        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        //        mLocationOption.setInterval(1000);
        //        mLocationOption.setGpsFirst(true);
        //        mLocationOption.setOnceLocationLatest(true);

    }


    /**
     * 开始定位
     */
    fun start() {
        init()
        //给定位客户端对象设置定位参数
        mLocationClient!!.setLocationOption(mLocationOption)
        //启动定位
        mLocationClient!!.startLocation()
        //        mLocationClient.startAssistantLocation();
    }

    /**
     * 停止定位
     */
    fun stop() {
        mLocationClient!!.stopLocation()
    }

    /**
     * 结束定位
     */
    fun destroy() {
        if (mLocationClient == null)
            return
        mLocationClient!!.stopLocation()//停止定位后，本地定位服务并不会被销毁
        mLocationClient!!.onDestroy()
        mLocationClient!!.stopLocation()
    }


    private var onLocationChange: OnLocationChangeListener? = null

    fun setOnLocationChange(onLocationChange: OnLocationChangeListener) {
        this.onLocationChange = onLocationChange
    }

    interface OnLocationChangeListener {
        /**
         * 当位置改变和第一次获取到位置的时候返回
         *
         * @param aMapLocation 位置数据
         */
        fun onLocationChange(aMapLocation: AMapLocation?)
    }

    companion object {
        private val TAG = "ILocationSever"
    }
}
