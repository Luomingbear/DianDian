package xyz.toofun.diandian.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * 注册账号使用的用户信息
 * Created by bear on 2017/3/29.
 */

data class RegisterUserBean(var email: String? = null, //邮箱
                            var password: String? = null, //密码

                            var nickname: String? = null, //昵称
                            var avatar: String? = null //头像)
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(email)
        writeString(password)
        writeString(nickname)
        writeString(avatar)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RegisterUserBean> = object : Parcelable.Creator<RegisterUserBean> {
            override fun createFromParcel(source: Parcel): RegisterUserBean = RegisterUserBean(source)
            override fun newArray(size: Int): Array<RegisterUserBean?> = arrayOfNulls(size)
        }
    }
}
