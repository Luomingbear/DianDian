package xyz.toofun.diandian.uitl.net

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.toofun.diandian.bean.TokenResponseBean
import xyz.toofun.diandian.uitl.FileUtil
import xyz.toofun.diandian.uitl.PasswordUtil
import java.io.File
import java.util.*

/**
 * 上传文件到七牛云
 * Created by bear on 2017/5/10.
 */

class QiniuUploadManager(context: Context) {
    private val mContext: Context
    private val mUploadManager: UploadManager //上传的管理
    private var mToken: String? = null //上传图片使用的token
    private var mPathList: MutableList<String>? = null //待上传的文件的地址
    private var mPicSize = 1f //需要上传的图片的总数量
    private val mCloudPathList: MutableList<String> //图片保存的地址
    private var mErrorPathList: MutableList<String>? = null //图片上传失败的
    private var mQiniuUploadInterface: QiniuUploadInterface? = null //上传完毕的监听
    private var mQiniuUploadProgressListener: QiniuUploadProgressListener? = null //上传进度监听
    private var mCurKey: String? = null //当前上传的文件的key
    private var mCurIndex = 0f //当前下载的是第几个
    private var progress = 0.0

    interface QiniuUploadInterface {
        fun onSucceed(pathList: List<String>)

        fun onFailed(errorPathList: List<String>?)
    }

    interface QiniuUploadProgressListener {
        fun onProgress(progress: Int)
    }

    fun setQiniuUploadInterface(mQiniuUploadInterface: QiniuUploadInterface) {
        this.mQiniuUploadInterface = mQiniuUploadInterface
    }

    fun setQiniuUploadProgressListener(mQiniuUploadProgressListener: QiniuUploadProgressListener) {
        this.mQiniuUploadProgressListener = mQiniuUploadProgressListener
    }

    init {
        mContext = context.getApplicationContext()
        val mConfiguration = Configuration.Builder().build()

        mUploadManager = UploadManager(mConfiguration)
        mPathList = ArrayList<String>()
        mCloudPathList = ArrayList<String>()
    }

    /**
     * 上传文件到七牛云
     *
     * @param filePath 文件的绝对路径
     */
    fun uploadFile(filePath: String) {
        if (TextUtils.isEmpty(filePath))
            return

        mPathList?.add(filePath)
        uploadFileList(mPathList)

        //需要上传的图片数量
        mPicSize = 1.0f
    }

    /**
     * 上传图片列表
     *
     * @param pathList
     */
    fun uploadFileList(pathList: MutableList<String>?) {
        if (pathList == null || pathList.size == 0)
            return

        mPathList = pathList

        //需要上传的图片数量
        mPicSize = mPathList!!.size.toFloat()

        //
        if (TextUtils.isEmpty(mToken))
            getTokenOnNet()
        else
            uploadFileOnNet(mPathList!!.get(0))
    }

    /**
     * 请求token
     */
    fun getTokenOnNet() {
        val tokenCall = RetrofitManager.getService().getQiniuToken
        tokenCall.enqueue(object : Callback<TokenResponseBean> {
            public override fun onResponse(call: Call<TokenResponseBean>, response: Response<TokenResponseBean>) {
                mToken = response.body()!!.data
                uploadFileOnNet(mPathList!!.get(0))
            }

            public override fun onFailure(call: Call<TokenResponseBean>, t: Throwable) {
                mErrorPathList = mPathList
                if (mQiniuUploadInterface != null)
                    mQiniuUploadInterface!!.onFailed(mErrorPathList)
            }
        })
    }

    /**
     * 上传图片
     *
     * @param path
     */
    private fun uploadFileOnNet(path: String) {

        val key = PasswordUtil.getSampleEncodePassword("" + System.currentTimeMillis() + FileUtil.getFileName(path)) +
                "." + FileUtil.getExtensionName(path)

        //压缩
        val compressFile: File
        if (FileUtil.getExtensionName(path) != "gif") {
            //            compressFile = CompressHelper.getDefault(mContext).compressToFile(new File(path));
            compressFile = File(path)

        } else {
            compressFile = File(path)
        }

        //
        mUploadManager.put(compressFile, key, mToken,
                object : UpCompletionHandler {
                    public override fun complete(key: String, info: ResponseInfo, res: JSONObject) {
                        mPathList!!.removeAt(0)

                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            Log.i("qiniu", "Upload Succeed")

                            mCloudPathList.add(UrlUtil.BASE_IMAGE_URL + key)

                            if (mPathList!!.size > 0) {
                                uploadFileOnNet(mPathList!!.get(0))
                            } else if (mQiniuUploadInterface != null) {
                                mQiniuUploadInterface!!.onSucceed(mCloudPathList)
                            }

                        } else {
                            Log.i("qiniu", "Upload Fail")
                            mErrorPathList!!.add(path)

                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                            if (mPathList!!.size > 0) {
                                uploadFileOnNet(mPathList!!.get(0))
                            } else if (mQiniuUploadInterface != null) {
                                mQiniuUploadInterface!!.onFailed(mErrorPathList)
                            }
                        }

                    }
                }, UploadOptions(null, null, false, object : UpProgressHandler {
            public override fun progress(key: String, percent: Double) {

                if (key != mCurKey) {
                    mCurKey = key
                    mCurIndex++
                } else {
                    //更新整体进度
                    progress = 95 * ((mCurIndex - 1) / mPicSize * (1 + percent))
                }

                if (mQiniuUploadProgressListener != null)
                    mQiniuUploadProgressListener!!.onProgress(progress.toInt())
            }
        }, null))
    }

    companion object {
        private val TAG = "QiniuUploadManager"
    }
}
