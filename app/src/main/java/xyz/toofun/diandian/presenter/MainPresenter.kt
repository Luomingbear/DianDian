package xyz.toofun.diandian.presenter

import android.content.Context
import android.view.View
import xyz.toofun.diandian.R
import xyz.toofun.diandian.fragment.ArticleMapFragment
import xyz.toofun.diandian.fragment.EditArticleFragment
import xyz.toofun.diandian.fragment.MenuFragment
import xyz.toofun.diandian.uitl.ToastUtil
import xyz.toofun.diandian.uitl.map.ILocationManager
import xyz.toofun.diandian.uitl.map.IMapManager
import xyz.toofun.diandian.view.MainView

/**
 * 主要的activity的逻辑
 * Created by bear on 2017/9/30.
 */
class MainPresenter(context: Context, view: MainView) : BasePresenter<MainView>(context, view), View.OnClickListener {

    private lateinit var mMapFragment: ArticleMapFragment //地图界面
    private lateinit var mMenuFragment: MenuFragment //左边菜单
    private lateinit var mEditArticleFragment: EditArticleFragment //写文章的fragment
    private lateinit var mMapManager: IMapManager //地图管家

    fun initSideslipLayout() {
        mMapFragment = ArticleMapFragment()
        mMenuFragment = MenuFragment()
        mEditArticleFragment = EditArticleFragment()

        mView?.sideslipLayout?.setFragmentManager(mView?.getFragemntManagerr())
//
        //加入左边侧滑菜单
        mView?.sideslipLayout?.setLeftFragment(mMenuFragment, 0.7f)

        //加入底部侧滑写故事
        mView?.sideslipLayout?.setBottomFragment(mEditArticleFragment, 1f)

    }


    /**
     * 初始化地图
     */
    fun initMap() {
        ILocationManager.instance.init(mContext?.applicationContext!!, mView?.getMapView()?.map)

        mView?.sideslipLayout?.getHomeView()?.findViewById(R.id.message)?.setOnClickListener(this)
        mView?.sideslipLayout?.getHomeView()?.findViewById(R.id.animate_position)?.setOnClickListener(this)
    }

    fun startLocation() {
        ILocationManager.instance.start()
    }

    fun destoryLocation() {
        ILocationManager.instance.destroy()
    }

    /**
     * 移动到用户所在的位置
     */
    fun animateUserPosition() {
        ILocationManager.instance.animate2CurrentPosition()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.message -> {
                ToastUtil.showToastR(mContext, R.string.message)
            }

            R.id.animate_position -> {
                animateUserPosition()
            }
        }
    }

}