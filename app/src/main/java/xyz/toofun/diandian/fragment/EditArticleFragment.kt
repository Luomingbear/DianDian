package xyz.toofun.diandian.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import xyz.toofun.diandian.R
import xyz.toofun.diandian.presenter.EditArticlePresenter
import xyz.toofun.diandian.view.EditArticleView
import xyz.toofun.diandian.widget.sideslip.SideslipLayout

/**
 * 写文章fragment
 * Created by bear on 2017/9/30.
 */
class EditArticleFragment : BaseFragment(), EditArticleView, View.OnClickListener {

    private lateinit var mPresenter: EditArticlePresenter
    private var mListener: SideslipLayout.OnSideLayoutClickListener? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_edit_article
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter = EditArticlePresenter(context, this)
    }

    override fun initEvent() {
        issueButton.setOnClickListener(this)
        hideButton.setOnClickListener(this)
    }

    override val issueButton: TextView
        get() = mRootView.findViewById(R.id.issue) as TextView

    override val hideButton: TextView
        get() = mRootView.findViewById(R.id.hide_edit_plan) as TextView

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.hide_edit_plan -> {
                mListener?.onClickHide()
            }

            R.id.issue -> mPresenter.clickIssue()
        }
    }


    fun setOnBottonSideLayoutClickListener(listener: SideslipLayout.OnSideLayoutClickListener) {
        this.mListener = listener
    }
}