package xyz.toofun.diandian.bean.read

/**
 * 标记已读点赞信息
 * Created by bear on 2017/5/25.
 */

class ReadStoryLikeBean {
    var userId: Int = 0
    var storyId: String? = null

    constructor() {}

    constructor(userId: Int, storyId: String) {
        this.userId = userId
        this.storyId = storyId
    }
}
