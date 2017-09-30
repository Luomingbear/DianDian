package xyz.toofun.diandian.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity


/**
 * activity 基类
 * Created by bear on 2017/9/30.
 */
abstract class BaseActivity : FragmentActivity() {

    val REQUEST_PERMISSION_STORAGE = 0x01 //存储
    val REQUEST_PERMISSION_CAMERA = 0x02 //相机
    val REQUEST_PERMISSION_IMAGE = 0x03 //图片
    val REQUEST_PERMISSION_LOCATION = 0x04 //定位

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        initView(savedInstanceState)

        initEvent()
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
        intent.setClass(this, cls)
        startActivity(intent)

    }

    fun intentWithFlag(cls: Class<*>, flag: Int) {
        val intent = Intent()
        intent.setClass(this, cls)
        intent.flags = flag
        startActivity(intent)
    }

    fun intentWithParcelable(cls: Class<*>, name: String, parcelable: Parcelable) {
        val intent = Intent()
        intent.setClass(this, cls)
        intent.putExtra(name, parcelable)
        startActivity(intent)

    }

    /**
     * 请求相机权限和文件存储权限
     */
    fun requestCameraPermission() {
        if (!checkPermission(Manifest.permission.CAMERA))
            ActivityCompat.requestPermissions(this, kotlin.arrayOf<String>(Manifest.permission.CAMERA), REQUEST_PERMISSION_CAMERA)
    }

    /**
     * 请求文件存储权限权限
     */
    fun requestStoragePermission() {
        if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            ActivityCompat.requestPermissions(this, kotlin.arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION_STORAGE)
    }

    /**
     * 请求定位权限
     */
    fun requestLocationPermission() {
        if (!checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION))
            ActivityCompat.requestPermissions(this, kotlin.arrayOf<String>(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_PERMISSION_LOCATION)
    }


    /**
     * 查看权限
     */
    fun checkPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

}