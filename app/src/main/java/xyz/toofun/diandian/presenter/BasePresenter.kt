package xyz.toofun.diandian.presenter

import android.content.Context
import xyz.toofun.diandian.view.BaseView

/**
 * presenter基类
 * Created by bear on 2017/9/30.
 */
open class BasePresenter<T : BaseView>(context: Context, view: T) {
    protected var mContext: Context? = null
    protected var mView: T? = null

    init {
        this.mContext = context
        this.mView = view
    }

    /**
     * 清除引用
     */
    fun onDestory() {
        this.mContext = null
        this.mView = null
    }
}