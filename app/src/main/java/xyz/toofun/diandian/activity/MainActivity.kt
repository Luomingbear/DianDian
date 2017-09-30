package xyz.toofun.diandian.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.amap.api.maps.MapView
import xyz.toofun.diandian.R
import xyz.toofun.diandian.presenter.MainPresenter
import xyz.toofun.diandian.view.MainView
import xyz.toofun.diandian.widget.sideslip.SideslipLayout


/**
 * 主要的activity
 */
class MainActivity : BaseActivity(), MainView {
    private val TAG = "MainActivity"
    private var mPresenter = MainPresenter(this, this)
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.initSideslipLayout()

        //地图必须写
        getMapView().onCreate(savedInstanceState)
    }

    override fun initEvent() {
        mPresenter.initMap()
    }

    override val sideslipLayout: SideslipLayout
        get() = findViewById(R.id.sideslip) as SideslipLayout

    override fun getFragemntManagerr(): FragmentManager {
        return supportFragmentManager
    }

    override fun getMapView(): MapView {
        return (sideslipLayout.getHomeView()?.findViewById(R.id.map_view) as MapView)
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        getMapView().onDestroy()
        mPresenter.destoryLocation()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        getMapView().onResume()
        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION))
            mPresenter.startLocation()
        else
            requestLocationPermission()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        getMapView().onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        getMapView().onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                requestLocationPermission()
            } else {
                //获取定位权限成功
                mPresenter.startLocation()
            }
        }
    }
}
