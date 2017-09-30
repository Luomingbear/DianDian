package xyz.toofun.diandian.presenter

import android.content.Context
import xyz.toofun.diandian.uitl.map.IMapManager
import xyz.toofun.diandian.view.ArticleMapView

/**
 * 地图界面的逻辑
 * Created by bear on 2017/9/30.
 */
class ArticleMapPresenter(context: Context, view: ArticleMapView) :
        BasePresenter<ArticleMapView>(context, view) {

    private lateinit var mMapManager: IMapManager

    fun initMap() {
        mMapManager = IMapManager(mContext!!, mView?.getMapView()?.map)
        mMapManager.init()
        mMapManager.animate2Position()
    }

}