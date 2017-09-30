package xyz.toofun.diandian.uitl

import android.content.Context
import android.text.TextUtils
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


/**
 * 文件工具
 * Created by bear on 2017/9/30.
 */
object FileUtil {
    /**
     * 复制文件到手机本地
     *
     * @param context
     * @param inName         assets里的文件名包含目录结构
     * @param strOutFileName 需要保存的地址
     * @throws IOException
     */
    @Throws(IOException::class)
    fun copyBigDataToSD(context: Context, inName: String, strOutFileName: String) {
        val myInput: InputStream
        val myOutput = FileOutputStream(strOutFileName)
        myInput = context.getAssets().open(inName)
        val buffer = ByteArray(1024)
        var length = myInput.read(buffer)
        while (length > 0) {
            myOutput.write(buffer, 0, length)
            length = myInput.read(buffer)
        }

        myOutput.flush()
        myInput.close()
        myOutput.close()
    }

    /**
     * 通过文件的路径获取文件名
     *
     * @param filePath 文件的路径
     * @return 文件名
     */
    fun getFileName(filePath: String): String? {
        if (!TextUtils.isEmpty(filePath)) {
            val start = filePath.lastIndexOf("/")
            val end = filePath.lastIndexOf(".")
            return if (start != -1 && end != -1) {
                filePath.substring(start + 1, end)
            } else {
                null
            }
        } else
            return ""
    }

    /**
     * 获取带后缀的文件名
     *
     * @param filePath
     * @return
     */
    fun getFileNameWithExtension(filePath: String): String? {
        if (!TextUtils.isEmpty(filePath)) {
            val start = filePath.lastIndexOf("/")
            return if (start != -1) {
                filePath.substring(start)
            } else {
                null
            }
        } else
            return ""
    }

    /**
     * 获取文件的后缀名
     *
     * @param filename 文件名
     * @return
     */
    fun getExtensionName(filename: String?): String? {
        if (filename != null && filename.length > 0) {
            val dot = filename.lastIndexOf('.')
            if (dot > -1 && dot < filename.length - 1) {
                return filename.substring(dot + 1)
            }
        }
        return filename
    }
}
