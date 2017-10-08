package xyz.toofun.diandian.fragment

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.util.Log
import com.amap.api.maps.MapView
import xyz.toofun.diandian.R
import xyz.toofun.diandian.presenter.ArticleMapPresenter
import xyz.toofun.diandian.view.ArticleMapView
import xyz.toofun.diandian.widget.sideslip.SideslipLayout

/**
 * 地图界面
 * Created by bear on 2017/9/30.
 */
class ArticleMapFragment : BaseFragment(), ArticleMapView {
    private val TAG = "ArticleMapFragment"
    private lateinit var mPresenter: ArticleMapPresenter
    private var mListener: SideslipLayout.OnSideLayoutClickListener? = null

    fun setOnBottonSideLayoutClickListener(listener: SideslipLayout.OnSideLayoutClickListener) {
        this.mListener = listener
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_map
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter = ArticleMapPresenter(context, this)

        getMapView().onCreate(savedInstanceState)
        Log.e(TAG, "create")

    }

    override fun initEvent() {
        mPresenter.initMap()
    }

    override fun getFragmentManagerr(): FragmentManager {
        return activity.supportFragmentManager
    }

    override fun getMapView(): MapView {
        return mRootView.findViewById(R.id.map_view) as MapView
    }


    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        getMapView().onDestroy()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        getMapView().onResume()

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
}