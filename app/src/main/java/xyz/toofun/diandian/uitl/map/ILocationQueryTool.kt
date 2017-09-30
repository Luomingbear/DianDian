package xyz.toofun.diandian.uitl.map

import android.content.Context
import android.util.Log
import com.amap.api.maps.model.LatLng
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.*
import com.amap.api.services.poisearch.PoiSearch

/**
 * 通过定位的数据获取逆编码信息
 * Created by bear on 2016/12/3.
 */

class ILocationQueryTool {
    private val TAG = "ILocationQueryTool"

    private lateinit var mContext: Context
    private var mGeocodeSearch: GeocodeSearch? = null //搜索实例

    protected constructor() {}

    constructor(context: Context) {
        this.mContext = context.applicationContext
    }

    /**
     * 搜索回调函数
     */
    private val onGeocodeSearchListener = object : GeocodeSearch.OnGeocodeSearchListener {
        override fun onRegeocodeSearched(regeocodeResult: RegeocodeResult?, i: Int) {
            if (i == 1000) {
                if (regeocodeResult != null && regeocodeResult.regeocodeAddress != null
                        && regeocodeResult.regeocodeAddress.formatAddress != null) {
                    val r = regeocodeResult.regeocodeAddress
                    Log.d(TAG, "onRegeocodeSearched: 搜索完毕！")
                    if (onLocationQueryListener != null)
                        onLocationQueryListener!!.onRegeocodeSearched(r)

                } else {
                    Log.i(TAG, "onRegeocodeSearched: regeocodeResult:" + regeocodeResult!!)
                }
            } else {
                Log.e(TAG, "onGeocodeSearched: " + i)
            }
        }

        override fun onGeocodeSearched(geocodeResult: GeocodeResult?, i: Int) {
            if (i == 1000) {
                if (geocodeResult != null && geocodeResult.geocodeAddressList != null
                        && geocodeResult.geocodeAddressList.size > 0) {
                    val addressList = geocodeResult.geocodeAddressList
                    if (onLocationQueryListener != null)
                        onLocationQueryListener!!.onGeocodeSearched(addressList)
                } else {
                    Log.i(TAG, "onGeocodeSearched: ")
                }
            } else {
                Log.e(TAG, "onGeocodeSearched: rCode:" + i)
            }
        }
    }

    /**
     * 开始指定坐标的搜索
     *
     * @param latLng 搜索的坐标 单位米
     * @param radius 搜索的半径 单位米
     */
    fun startRegeocodeQuery(latLng: LatLng, radius: Int) {
        var radius = radius
        if (mContext == null)
            return
        mGeocodeSearch = GeocodeSearch(mContext)

        val latLonPoint = LatLonPoint(latLng.latitude, latLng.longitude)
        mGeocodeSearch!!.setOnGeocodeSearchListener(onGeocodeSearchListener)
        //最小的搜索范围 10米
        if (radius < 10)
            radius = 10
        val geocodeQuery = RegeocodeQuery(latLonPoint, radius.toFloat(), GeocodeSearch.AMAP)
        mGeocodeSearch!!.getFromLocationAsyn(geocodeQuery)
    }

    /***
     * 开始模糊搜索
     * @param location 需要查询的地点
     * @param cityName 城市的中文拼音或者中文，null则表示全国
     */
    fun startGeocodeQuery(location: String, cityName: String) {
        if (mContext == null || onGeocodeSearchListener == null)
            return
        mGeocodeSearch = GeocodeSearch(mContext)
        mGeocodeSearch!!.setOnGeocodeSearchListener(onGeocodeSearchListener)
        val geocodeQuery = GeocodeQuery(location, cityName)
        mGeocodeSearch!!.getFromLocationNameAsyn(geocodeQuery)
    }

    /**
     * 开始poi搜索
     *
     * @param poiName poi的描述
     */
    fun startPoiSearch(poiName: String, cityName: String, onPoiSearchListener: PoiSearch.OnPoiSearchListener?) {
        if (mContext == null || onPoiSearchListener == null)
            return
        val query = PoiSearch.Query(poiName, "风景名胜|生活服务|商务住宅|餐饮服务|科教文化服务|政府机构及社会团体|交通设施服务|公司企业", cityName)
        query.pageSize = 15// 设置每页最多返回多少条poiitem
        val poiSearch = PoiSearch(mContext, query)
        poiSearch.setOnPoiSearchListener(onPoiSearchListener)

        poiSearch.searchPOIAsyn() //发送请求
    }


    private var onLocationQueryListener: OnLocationQueryListener? = null

    fun setOnLocationQueryListener(onLocationQueryListener: OnLocationQueryListener) {
        this.onLocationQueryListener = onLocationQueryListener
    }

    interface OnLocationQueryListener {
        /***
         * 搜索经纬度得到的值
         */
        fun onRegeocodeSearched(regeocodeAddress: RegeocodeAddress)

        /**
         * 通过粗略位置获取的值
         */
        fun onGeocodeSearched(geocodeAddressList: List<GeocodeAddress>)
    }
}
