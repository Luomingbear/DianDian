package xyz.toofun.diandian.bean.message


import xyz.toofun.diandian.bean.BaseUserInfo

/**
 * 故事获得的赞
 * Created by bear on 2017/5/24.
 */

class StoryLikeBean {
    var userInfo: BaseUserInfo? = null
    var likeId: String? = null
    var storyId: String? = null
    var content: String? = null
    var cover: String? = null
    var createTime: String? = null

    constructor() {}

    constructor(userInfo: BaseUserInfo, likeId: String, storyId: String, content: String,
                cover: String, createTime: String) {
        this.userInfo = userInfo
        this.likeId = likeId
        this.storyId = storyId
        this.content = content
        this.cover = cover
        this.createTime = createTime
    }
}
