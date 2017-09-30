package xyz.toofun.diandian.uitl

import android.app.Activity
import android.content.Context
import com.amap.api.maps.model.LatLng


/**
 * 简单数据的本地保存
 * Created by bear on 2017/9/30.
 */
object SharePreferenceManager {
    /**
     * 用户数据
     */
    val USER_DATA = "userData"
    private val NICK_NAME = "nickName" //昵称
    private val AVATAR = "avatar" //头像
    private val USER_ID = "userId" //roomId
    private val PASSWORD = "password" //password


    /**
     * 故事数据
     */
    val STORY_DATA = "storyData"
    private val CONTENT = "content" //正文
    private val TITLE = "title" //标题
    private val COVER_PIC = "coverPic" //封面图
    private val EXTRA = "extra" //摘要

    /**
     * 定位数据
     */
    val LOCATION_DATA = "locationData"
    val CITY_NAME = "cityName"
    val LAT = "lat"
    val LNG = "lng"


    /**
     * 登录信息
     */
    val LOGIN_DATA = "LOGIN_DATA"
    val IS_LOGIN = "isLogin"
    val IS_NIGHT_MODE = "isNight" //夜间模式


//    public static void saveUserData(Context context, BaseUserInfo userInfo) {
//        SharedPreferences sp = context.getSharedPreferences(USER_DATA,
//                Activity.MODE_PRIVATE);
//        // 获取Editor对象
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(NICK_NAME, userInfo.getNickname());
//        editor.putString(AVATAR, userInfo.getAvatar());
//        editor.putInt(USER_ID, userInfo.getUserId());
//        editor.apply();
//    }

    /**
     * 保存使用者的用户id
     *
     * @param context
     * @param userId
     */
    fun saveUserId(context: Context, userId: Int) {
        val sp = context.getSharedPreferences(USER_DATA,
                Activity.MODE_PRIVATE)
        // 获取Editor对象
        val editor = sp.edit()
        editor.putInt(USER_ID, userId)
        editor.apply()
    }

    /**
     * 保存使用者的用password
     *
     * @param context
     * @param password
     */
    fun savePassword(context: Context, password: String) {
        val sp = context.getSharedPreferences(USER_DATA,
                Activity.MODE_PRIVATE)
        // 获取Editor对象
        val editor = sp.edit()
        editor.putString(PASSWORD, password)
        editor.apply()
    }

    /**
     * 移除用户数据
     *
     * @param context
     */
    fun removeUserInfo(context: Context) {
        val sp = context.getSharedPreferences(USER_DATA,
                Activity.MODE_PRIVATE)
        // 获取Editor对象
        val editor = sp.edit()
        editor.remove(USER_ID)
        editor.remove(NICK_NAME)
        editor.remove(AVATAR)
        editor.apply()

    }

    /**
     * 获取用户的id
     *
     * @return
     */
    fun getUserId(context: Context): Int {
        val sp = context.getSharedPreferences(USER_DATA,
                Activity.MODE_PRIVATE)
        return sp.getInt(USER_ID, -1)
    }

    /**
     * 获取用户的password
     *
     * @return
     */
    fun getPassword(context: Context): String {
        val sp = context.getSharedPreferences(USER_DATA,
                Activity.MODE_PRIVATE)
        return sp.getString(PASSWORD, "")
    }

    fun saveNickname(context: Context, nickname: String) {
        val sp = context.getSharedPreferences(USER_DATA,
                Activity.MODE_PRIVATE)
        // 获取Editor对象
        val editor = sp.edit()
        editor.putString(NICK_NAME, nickname)
        editor.apply()
    }

    fun getNickName(context: Context): String {
        val sp = context.getSharedPreferences(USER_DATA,
                Activity.MODE_PRIVATE)
        return sp.getString(NICK_NAME, "")
    }

    fun saveAvatar(context: Context, avatar: String) {
        val sp = context.getSharedPreferences(USER_DATA,
                Activity.MODE_PRIVATE)
        // 获取Editor对象
        val editor = sp.edit()
        editor.putString(AVATAR, avatar)
        editor.apply()
    }

    fun getAVATAR(context: Context): String {
        val sp = context.getSharedPreferences(USER_DATA,
                Activity.MODE_PRIVATE)
        return sp.getString(AVATAR, "")
    }


    /**
     * 保存正在编辑的故事标题
     *
     * @param context
     * @param title
     */
    fun saveTitle(context: Context, title: String) {
        val sp = context.getSharedPreferences(STORY_DATA,
                Activity.MODE_PRIVATE)
        // 获取Editor对象
        val editor = sp.edit()
        editor.putString(TITLE, title)
        editor.apply()
    }

    /**
     * 保存正在编辑的正文
     *
     * @param context
     * @param content
     */
    fun saveContent(context: Context, content: String) {
        val sp = context.getSharedPreferences(STORY_DATA,
                Activity.MODE_PRIVATE)
        // 获取Editor对象
        val editor = sp.edit()
        editor.putString(CONTENT, content)
        editor.apply()
    }

    /**
     * 保存选择的封面图
     *
     * @param context
     * @param coverPicUrl
     */
    fun saveCoverPic(context: Context, coverPicUrl: String) {
        val sp = context.getSharedPreferences(STORY_DATA,
                Activity.MODE_PRIVATE)
        // 获取Editor对象
        val editor = sp.edit()
        editor.putString(COVER_PIC, coverPicUrl)
        editor.apply()
    }

    /**
     * 保存正在编辑的摘要
     *
     * @param context
     * @param extra
     */
    fun saveExtra(context: Context, extra: String) {
        val sp = context.getSharedPreferences(STORY_DATA,
                Activity.MODE_PRIVATE)
        // 获取Editor对象
        val editor = sp.edit()
        editor.putString(EXTRA, extra)
        editor.apply()
    }

    /**
     * 获取标题
     *
     * @param context
     * @return
     */
    fun getTitle(context: Context): String {
        val sp = context.getSharedPreferences(STORY_DATA,
                Activity.MODE_PRIVATE)
        return sp.getString(TITLE, "")
    }

    /**
     * 获取正文
     *
     * @param context
     * @return
     */
    fun getContent(context: Context): String {
        val sp = context.getSharedPreferences(STORY_DATA,
                Activity.MODE_PRIVATE)
        return sp.getString(CONTENT, "")
    }

    /**
     * 获取选择的封面图
     *
     * @param context
     * @return
     */
    fun getCoverPic(context: Context): String {
        val sp = context.getSharedPreferences(STORY_DATA,
                Activity.MODE_PRIVATE)
        return sp.getString(COVER_PIC, "")
    }

    /**
     * 获取编辑的摘要
     *
     * @param context
     * @return
     */
    fun getExtra(context: Context): String {
        val sp = context.getSharedPreferences(STORY_DATA,
                Activity.MODE_PRIVATE)
        return sp.getString(EXTRA, "")
    }

    /**
     * 保存最近的一次经纬度
     */
    fun saveLatLng(context: Context, latLng: LatLng) {
        val sp = context.getSharedPreferences(LOCATION_DATA,
                Activity.MODE_PRIVATE)
        // 获取Editor对象
        val editor = sp.edit()

        editor.putFloat(LAT, latLng.latitude.toFloat())
        editor.putFloat(LNG, latLng.longitude.toFloat())
        editor.apply()
    }

    /**
     * 获取上次保存的位置信息
     *
     * @return 经纬度
     */
    fun getLatLngData(context: Context): LatLng {
        val sp = context.getSharedPreferences(LOCATION_DATA,
                Activity.MODE_PRIVATE)
        /**
         * latitude - 地点的纬度，在-90 与90 之间的double 型数值。
         * longitude - 地点的经度，在-180 与180 之间的double 型数值。
         */

        return LatLng(sp.getFloat(LAT, 30f).toDouble(), sp.getFloat(LNG, 120f).toDouble())
    }

    /**
     * 保存用户所在的城市名字
     *
     * @param context
     * @param cityName
     */
    fun saveCityName(context: Context, cityName: String) {
        val sp = context.getSharedPreferences(LOCATION_DATA,
                Activity.MODE_PRIVATE)
        // 获取Editor对象
        val editor = sp.edit()

        editor.putString(CITY_NAME, cityName)
        editor.apply()
    }

    /**
     * 获取用户所在的城市名字
     *
     * @param context
     */
    fun getCityName(context: Context): String {
        val sp = context.getSharedPreferences(LOCATION_DATA,
                Activity.MODE_PRIVATE)
        return sp.getString(CITY_NAME, "")
    }

    /**
     * 登录成功
     */
    fun setIsLogin(context: Context, isFirstLogin: Boolean) {
        val sp = context.getSharedPreferences(LOGIN_DATA,
                Activity.MODE_PRIVATE)
        // 获取Editor对象
        val editor = sp.edit()
        editor.putBoolean(IS_LOGIN, isFirstLogin)

        editor.apply()
    }

    /**
     * 是否完成登录
     *
     * @return 经纬度
     */
    fun isLogin(context: Context): Boolean {
        val sp = context.getSharedPreferences(LOGIN_DATA,
                Activity.MODE_PRIVATE)

        return sp.getBoolean(IS_LOGIN, true)
    }

    /**
     * 登录成功
     */
    fun setIsNightMode(context: Context, isNightMode: Boolean) {
        val sp = context.getSharedPreferences(LOGIN_DATA,
                Activity.MODE_PRIVATE)
        // 获取Editor对象
        val editor = sp.edit()
        editor.putBoolean(IS_NIGHT_MODE, isNightMode)

        editor.apply()
    }

    /**
     * 是否完成登录
     *
     * @return 经纬度
     */
    fun isNightMode(context: Context): Boolean {
        val sp = context.getSharedPreferences(LOGIN_DATA,
                Activity.MODE_PRIVATE)

        return sp.getBoolean(IS_NIGHT_MODE, false)
    }
}