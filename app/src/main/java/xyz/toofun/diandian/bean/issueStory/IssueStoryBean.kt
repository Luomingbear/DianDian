package xyz.toofun.diandian.bean.issueStory

/**
 * 创建故事时需要上传的数据
 * Created by bear on 2017/5/11.
 */

class IssueStoryBean : BaseIssueStoryBean {
    private var cover: String? = null //故事的封面图
    var storyPictures: List<String>? = null //配图

    constructor() {}

    constructor(cover: String, storyPictures: List<String>) {
        this.cover = cover
        this.storyPictures = storyPictures
    }

    constructor(userId: Int, content: String, cityName: String, locationTitle: String, latitude: Double, longitude: Double, createTime: String, destroyTime: String, isAnonymous: Boolean?,
                storyType: Int, cover: String, storyPictures: List<String>) : super(userId, content, cityName, locationTitle, latitude, longitude, createTime, destroyTime, isAnonymous, storyType) {
        this.cover = cover
        this.storyPictures = storyPictures
    }

    fun getCover(): String? {
        return if (storyPictures != null && storyPictures!!.size > 0) storyPictures!![0] else null

    }

    fun setCover(cover: String) {
        if (storyPictures != null && storyPictures!!.size > 0)
            this.cover = storyPictures!![0]
    }

}
