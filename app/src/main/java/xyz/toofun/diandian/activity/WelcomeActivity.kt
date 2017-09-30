package xyz.toofun.diandian.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_welcome_layout.*
import xyz.toofun.diandian.R
import xyz.toofun.diandian.uitl.FileUtil
import xyz.toofun.diandian.uitl.NameUtil
import java.io.File
import java.io.IOException
import java.util.*

/**
 * 欢迎页
 * 定时3秒之后自动跳转到地图页面
 * Created by bear on 2016/12/20.
 */

class WelcomeActivity : BaseActivity() {
    private val TAG = "WelcomeActivity"

    private var AdImage: ImageView? = null //广告页
    private var Describe: TextView? = null //广告描述
    private val mWaitTime = 4000 //自动跳转等待的时间 单位毫秒
    private val mAnimationTime = 1600 //动画执行时间，单位毫秒
    private val skipTime = 2 * 1000
    private var spendTime = 0
    private var mTimer: Timer? = null//定时器


    override fun onResume() {
        super.onResume()
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_welcome_layout
    }

    override fun initView(savedInstanceState: Bundle?) {
        AdImage = findViewById(R.id.ad_img) as ImageView

        Describe = findViewById(R.id.describe) as TextView
    }

    override fun initEvent() {
        initTimer()

        copyMapStyleFile()

        getLaunchImg()

        welcome_skip.setOnClickListener {
            if (mTimer != null)
                mTimer!!.cancel()
            //跳转到地图界面
            checkIsFirst()
        }
    }

    /**
     * 复制自定义的地图文件
     */
    fun copyMapStyleFile() {
        val thead = Thread()
        thead.run {
            //将自定义地图的文件复制到内存卡
            var outPath = filesDir.absolutePath
            //        Log.i(TAG, "initEvent: outPath：" + outPath);
            outPath = outPath + File.separator + NameUtil.MAP_STYLE
            try {
                val file = File(outPath)
                if (!file.exists())
                    FileUtil.copyBigDataToSD(baseContext, NameUtil.MAP_STYLE, outPath)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        thead.start()
    }


    /**
     * 获取服务器发送的启动图片
     */
    fun getLaunchImg() {
        //        Call<LauncherResponseBean> call = RetrofitManager.getInstance().getService().getLauncher();
        //        call.enqueue(new Callback<LauncherResponseBean>() {
        //            @Override
        //            public void onResponse(Call<LauncherResponseBean> call, Response<LauncherResponseBean> response) {
        //                Glide.with(WelcomeActivity.this).load(response.body().getUrl()).into(AdImage);
        //                Describe.setText(response.body().getDescribe());
        //
        //                initTimer();
        //
        //            }
        //
        //            @Override
        //            public void onFailure(Call<LauncherResponseBean> call, Throwable t) {
        //                Log.e(TAG, "onFailure: " + t);
        //                initTimer();
        //
        //            }
        //        });

    }


    /**
     * 播放logo动画
     */
    private fun startLogoAnimation() {

    }

    /**
     * 设置自动跳转到地图界面
     */
    private fun initTimer() {
        mTimer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                spendTime += 1000
                runOnUiThread {
                    welcome_skip.text = getString(R.string.skip, (mWaitTime - spendTime) / 1000)
                }
                if (spendTime == mWaitTime) {
                    checkIsFirst()
                }
            }
        }
        mTimer!!.schedule(timerTask, 0, 1000)
    }

    /**
     * 检查是否是第一次登录
     */
    fun checkIsFirst() {
//        val isFirstStart = SharePreferenceManager.isFirst(this)
//        if (isFirstStart) {
//            if (SharePreferenceManager.getUserId(this) == -1)
////            intentWithFlag(LoginActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            else
//                intentWithFlag(MainActivity::class.java, Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        } else
        intentWithFlag(MainActivity::class.java, Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

    }

}