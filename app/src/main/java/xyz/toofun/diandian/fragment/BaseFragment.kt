package xyz.toofun.diandian.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * fragment的基类
 * Created by bear on 2017/9/30.
 */
abstract class BaseFragment : Fragment() {
    private val  TAG = "BaseFragment"
    protected lateinit var mRootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(getLayoutId(), container, false)
        Log.d(TAG,"onrecteView")
        initView(savedInstanceState)

        initEvent()
        return mRootView
    }

    /**
     * 获取布局id
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化事件
     */
    abstract fun initEvent()

    /**
     * 页面跳转
     *
     * @param cls
     */
    fun intentTo(cls: Class<*>) {
        val intent = Intent()
        intent.setClass(activity, cls)
        startActivity(intent)

    }

    fun intentWithFlag(cls: Class<*>, flag: Int) {
        val intent = Intent()
        intent.setClass(activity, cls)
        intent.flags = flag
        startActivity(intent)
    }

    fun intentWithParcelable(cls: Class<*>, name: String, parcelable: Parcelable) {
        val intent = Intent()
        intent.setClass(activity, cls)
        intent.putExtra(name, parcelable)
        startActivity(intent)

    }
}