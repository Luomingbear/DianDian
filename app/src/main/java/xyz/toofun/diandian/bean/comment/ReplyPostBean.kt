package xyz.toofun.diandian.bean.comment

/**
 * 回复的数据结构
 * Created by bear on 2017/6/29.
 */

class ReplyPostBean : CommentPostBean {
    var replyId: String? = null //回复的评论对象的commentId


    constructor() {}

    constructor(storyId: String, userId: Int, comment: String, createTime: String, replyId: String) : super(storyId, userId, comment, createTime) {
        this.replyId = replyId
    }
}
