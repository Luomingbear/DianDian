package xyz.toofun.diandian.presenter

import android.content.Context
import xyz.toofun.diandian.R
import xyz.toofun.diandian.uitl.ToastUtil
import xyz.toofun.diandian.view.EditArticleView

/**
 * 写文章页面的逻辑处理
 * Created by bear on 2017/9/30.
 */
class EditArticlePresenter(context: Context, view: EditArticleView) :
        BasePresenter<EditArticleView>(context, view) {

    fun clickIssue() {
        ToastUtil.showToastR(mContext, R.string.issue)

    }

    fun clickHide() {
        ToastUtil.showToastR(mContext, R.string.hide_edit_plan)
    }


}