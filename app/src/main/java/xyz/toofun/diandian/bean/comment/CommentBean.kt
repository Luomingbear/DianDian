package xyz.toofun.diandian.bean.comment


import xyz.toofun.diandian.bean.BaseUserInfo

/**
 * 单个评论的数据
 * Created by bear on 2017/5/15.
 */

class CommentBean {
    var userInfo: BaseUserInfo? = null
    var commentId: String? = null
    var storyId: String? = null
    var comment: String? = null
    var createTime: String? = null
    var likeNum: Int = 0
    var opposeNum: Int = 0
    var tag: String? = null
    var commentType = COMMENT //评论的类型，是评论文章还是回复的评论

    var reply: String? = null //回复的内容
    var replyUser: String? = null //被回复的用户的昵称

    constructor() {}

    constructor(userInfo: BaseUserInfo, commentId: String, storyId: String, comment: String,
                createTime: String, likeNum: Int, opposeNum: Int, tag: String, commentType: Int,
                reply: String, replyUser: String) {
        this.userInfo = userInfo
        this.commentId = commentId
        this.storyId = storyId
        this.comment = comment
        this.createTime = createTime
        this.likeNum = likeNum
        this.opposeNum = opposeNum
        this.tag = tag
        this.commentType = commentType
        this.reply = reply
        this.replyUser = replyUser
    }

    companion object {

        val COMMENT = 0 //评论
        val REPLY = 1 //回复
    }
}
