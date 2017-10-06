package xyz.toofun.diandian.bean.getStory

import android.os.Parcel
import android.os.Parcelable
import xyz.toofun.diandian.bean.CardInfo

/**
 * 一个故事包含的完整信息
 * Created by bear on 2017/5/12.
 */

data class StoryBean(var storyPictures: List<String>? = null) : CardInfo(), Parcelable {
    constructor(source: Parcel) : this(
            source.createStringArrayList()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeStringList(storyPictures)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StoryBean> = object : Parcelable.Creator<StoryBean> {
            override fun createFromParcel(source: Parcel): StoryBean = StoryBean(source)
            override fun newArray(size: Int): Array<StoryBean?> = arrayOfNulls(size)
        }
    }
}

