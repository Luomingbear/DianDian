package xyz.toofun.diandian.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * 故事卡片信息类
 * Created by bear on 2016/12/4.
 */

open class CardInfo(var userInfo: BaseUserInfo? = null,
                    var storyId: String? = null,
                    var content: String? = null,
                    var cover: String? = null,
                    var locationTitle: String? = null,
                    var latitude: Double = 0.toDouble(),
                    var longitude: Double = 0.toDouble(),
                    var createTime: String? = null,
                    var destroyTime: String? = null,
                    var anonymous: Boolean? = null,
                    var like: Boolean? = false, //点赞
                    var oppose: Boolean? = false, //反对
                    var title: String? = null, //文章的标题
                    var storyType: Int = STORY, //故事的类型：短文；文章；视频

                    var likeNum: Int = 0,
                    var opposeNum: Int = 0,
                    var commentNum: Int = 0) : Parcelable {
    constructor(source: Parcel) : this(
            source.readParcelable<BaseUserInfo>(BaseUserInfo::class.java.classLoader),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readDouble(),
            source.readDouble(),
            source.readString(),
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readString(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(userInfo, 0)
        writeString(storyId)
        writeString(content)
        writeString(cover)
        writeString(locationTitle)
        writeDouble(latitude)
        writeDouble(longitude)
        writeString(createTime)
        writeString(destroyTime)
        writeValue(anonymous)
        writeValue(like)
        writeValue(oppose)
        writeString(title)
        writeInt(storyType)
        writeInt(likeNum)
        writeInt(opposeNum)
        writeInt(commentNum)
    }

    companion object {
        val STORY = 0 //类似微博，140字以内

        val ARTICLE = 1 //类似简书，不限字数，有标题

        val VIDEO = 2 //视频

        @JvmField
        val CREATOR: Parcelable.Creator<CardInfo> = object : Parcelable.Creator<CardInfo> {
            override fun createFromParcel(source: Parcel): CardInfo = CardInfo(source)
            override fun newArray(size: Int): Array<CardInfo?> = arrayOfNulls(size)
        }
    }
}
