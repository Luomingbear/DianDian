package xyz.toofun.diandian.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * 用户数据信息
 * Created by bear on 2016/12/13.
 */

data class BaseUserInfo(var userId: Int = VISITOR, //用户id 默认游客身份
                        var nickname: String? = null, //昵称
                        var avatar: String? = null //头像
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(userId)
        writeString(nickname)
        writeString(avatar)
    }

    companion object {

        val VISITOR = -1 //游客id

        @JvmField
        val CREATOR: Parcelable.Creator<BaseUserInfo> = object : Parcelable.Creator<BaseUserInfo> {
            override fun createFromParcel(source: Parcel): BaseUserInfo = BaseUserInfo(source)
            override fun newArray(size: Int): Array<BaseUserInfo?> = arrayOfNulls(size)
        }
    }
}
