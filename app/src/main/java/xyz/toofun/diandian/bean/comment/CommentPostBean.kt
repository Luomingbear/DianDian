package xyz.toofun.diandian.bean.comment

/**
 * 上传的评论内容
 * Created by bear on 2017/5/15.
 */

open class CommentPostBean {
    var storyId: String? = null
    var userId: Int = 0
    var comment: String? = null
    var createTime: String? = null

    constructor() {}

    constructor(storyId: String, userId: Int, comment: String, createTime: String) {
        this.storyId = storyId
        this.userId = userId
        this.comment = comment
        this.createTime = createTime
    }
}
