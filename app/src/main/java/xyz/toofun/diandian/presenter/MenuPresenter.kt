package xyz.toofun.diandian.presenter

import android.content.Context
import xyz.toofun.diandian.R
import xyz.toofun.diandian.uitl.ToastUtil
import xyz.toofun.diandian.view.MenuView

/**
 * 左侧滑菜单的逻辑
 * Created by bear on 2017/9/30.
 */
class MenuPresenter(context: Context, view: MenuView) :
        BasePresenter<MenuView>(context, view) {

    fun clickMyFootmark() {
        ToastUtil.showToastR(mContext, R.string.my_footmark)
    }

    fun clickMyAttire() {
        ToastUtil.showToastR(mContext, R.string.my_attire)

    }
}