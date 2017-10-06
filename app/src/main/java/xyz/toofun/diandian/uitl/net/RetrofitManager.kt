package xyz.toofun.diandian.uitl.net


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络请求的manager
 * 单例模式
 * Created by bear on 2017/5/10.
 */

object RetrofitManager {
    private val mRetrofit: Retrofit

    /**
     * 获取服务
     *
     * @return
     */
    private var service: ApiService //请求的接口

    init {
        /**
         * 构建retrofit
         */
        mRetrofit = Retrofit.Builder()
                .baseUrl(UrlUtil.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        /**
         * 设置请求
         */
        service = mRetrofit.create(ApiService::class.java)
    }

    fun getService(): ApiService {
        return service
    }

}
