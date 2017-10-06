package xyz.toofun.diandian.uitl

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * 密码加密工具类
 * Created by bear on 16/11/10.
 */

object PasswordUtil {
    /**
     * 获取经过简单MD5加密的密码，只进行一次加密
     *
     * @param password 需要加密的密码
     * @return string 经过加密的密码
     * @throws NoSuchAlgorithmException
     */
    fun getSampleEncodePassword(password: String): String? {
        try {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(password.toByteArray())).toString(16)

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * \
     * 获取经过两次MD5加密的密码
     *
     * @param password
     * @return
     */
    fun getDoubleEncodePassword(password: String): String? {
        try {
            val md = MessageDigest.getInstance("MD5")
            val once = BigInteger(1, md.digest(password.toByteArray())).toString(16)
            return BigInteger(1, md.digest(once.toByteArray())).toString(16)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 获取经过MD5用户名和密码双重加密的密码
     *
     * @param username
     * @param password
     * @return
     */
    fun getEncodeUsernamePassword(username: String, password: String): String {
        val pwd = username + password
        var md: MessageDigest? = null
        try {
            md = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return BigInteger(1, md!!.digest(pwd.toByteArray())).toString(16)
    }

}