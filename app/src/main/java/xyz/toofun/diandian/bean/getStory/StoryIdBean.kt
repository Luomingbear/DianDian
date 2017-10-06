package xyz.toofun.diandian.bean.getStory

import android.os.Parcel
import android.os.Parcelable

/**
 * 包含故事id的bean
 * 用于请求故事信息
 * Created by bear on 2017/5/12.
 */

data class StoryIdBean(var storyId: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(storyId)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StoryIdBean> = object : Parcelable.Creator<StoryIdBean> {
            override fun createFromParcel(source: Parcel): StoryIdBean = StoryIdBean(source)
            override fun newArray(size: Int): Array<StoryIdBean?> = arrayOfNulls(size)
        }
    }
}
