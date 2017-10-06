package xyz.toofun.diandian.bean.message


import xyz.toofun.diandian.bean.BaseUserInfo

/**
 * 单个故事评论信息
 * Created by bear on 2017/5/24.
 */

class StoryCommentBean {
    var userInfo: BaseUserInfo? = null
    var storyId: String? = null
    var commentId: String? = null
    var comment: String? = null
    var content: String? = null
    var cover: String? = null
    var createTime: String? = null

    constructor() {}

    constructor(userInfo: BaseUserInfo, storyId: String, commentId: String,
                comment: String, content: String, cover: String, createTime: String) {
        this.userInfo = userInfo
        this.storyId = storyId
        this.commentId = commentId
        this.comment = comment
        this.content = content
        this.cover = cover
        this.createTime = createTime
    }
}
