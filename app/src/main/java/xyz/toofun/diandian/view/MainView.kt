package xyz.toofun.diandian.view

import android.support.v4.app.FragmentManager
import com.amap.api.maps.MapView
import xyz.toofun.diandian.widget.sideslip.SideslipLayout

/**
 * 主要的activity的view接口
 * Created by bear on 2017/9/30.
 */
interface MainView : BaseView {

    val sideslipLayout: SideslipLayout

    fun getFragemntManagerr(): FragmentManager

    fun getMapView(): MapView

}