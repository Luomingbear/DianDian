package xyz.toofun.diandian.fragment

import android.os.Bundle
import android.view.View
import xyz.toofun.diandian.R
import xyz.toofun.diandian.presenter.MenuPresenter
import xyz.toofun.diandian.view.MenuView

/**
 * 左侧菜单的fragment
 * Created by bear on 2017/9/30.
 */
class MenuFragment : BaseFragment(), MenuView, View.OnClickListener {
    private lateinit var mPresenter: MenuPresenter

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.my_attire_tv -> {
                mPresenter.clickMyAttire()
            }

            R.id.my_footmark_tv -> {
                mPresenter.clickMyFootmark()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_menu
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter = MenuPresenter(context, this)
    }

    override fun initEvent() {
        mRootView.findViewById(R.id.my_footmark_tv).setOnClickListener(this)

        mRootView.findViewById(R.id.my_attire_tv).setOnClickListener(this)
    }
}