package xyz.toofun.diandian.fragment

import android.os.Bundle
import xyz.toofun.diandian.R
import xyz.toofun.diandian.presenter.EditArticlePresenter
import xyz.toofun.diandian.view.EditArticleView

/**
 * 写文章fragment
 * Created by bear on 2017/9/30.
 */
class EditArticleFragment : BaseFragment(), EditArticleView {
    private lateinit var mPresenter :EditArticlePresenter

    override fun getLayoutId(): Int {
        return R.layout.fragment_edit_article
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter =EditArticlePresenter(context, this)
    }

    override fun initEvent() {
    }
}