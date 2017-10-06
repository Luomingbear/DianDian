package xyz.toofun.diandian.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * 正文里面的每一段的内容
 * Created by bear on 2016/12/29.
 */

data class EditLineData(var inputStr: String? = null,
                        var iamgePath: String? = null) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(inputStr)
        writeString(iamgePath)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<EditLineData> = object : Parcelable.Creator<EditLineData> {
            override fun createFromParcel(source: Parcel): EditLineData = EditLineData(source)
            override fun newArray(size: Int): Array<EditLineData?> = arrayOfNulls(size)
        }
    }
}
