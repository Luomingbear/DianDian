package xyz.toofun.diandian.view

import android.support.v4.app.FragmentManager
import com.amap.api.maps.MapView

/**
 * 地图界面的view
 * Created by bear on 2017/9/30.
 */
interface ArticleMapView : BaseView {
    fun getMapView(): MapView

    fun getFragmentManagerr():FragmentManager
}