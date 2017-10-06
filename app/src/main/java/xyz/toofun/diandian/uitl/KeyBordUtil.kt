package xyz.toofun.diandian.uitl

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*

/**
 * 控制键盘的弹出于隐藏的工具
 * Created by bear on 2017/3/31.
 */

object KeyBordUtil {
    /**
     * 显示键盘
     */
    fun showKeyboard(context: Context, view: View?) {
        /***
         * 由于界面为加载完全而无法弹出软键盘所以延时一段时间弹出键盘
         */
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(view, 0)
            }
        },
                180)
    }

    /**
     * 隐藏键盘
     */
    fun hideKeyboard(context: Context, view: View?) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}
