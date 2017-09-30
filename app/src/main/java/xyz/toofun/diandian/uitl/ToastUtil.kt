package xyz.toofun.diandian.uitl

import android.content.Context
import android.widget.Toast

/**
 * toast工具
 * Created by bear on 2017/9/30.
 */
object ToastUtil {
    fun showToastS(context: Context?, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun showToastR(context: Context?, textRes: Int) {
        Toast.makeText(context, textRes, Toast.LENGTH_SHORT).show()
    }
}