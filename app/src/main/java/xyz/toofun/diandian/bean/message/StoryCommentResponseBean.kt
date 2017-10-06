package xyz.toofun.diandian.bean.message


import xyz.toofun.diandian.bean.BaseResponseBean

/**
 * 获取用户故事未读评论的返回值
 *
 *
 * Created by bear on 2017/5/24.
 */

class StoryCommentResponseBean : BaseResponseBean {
    var data: List<StoryCommentBean>? = null

    constructor() {}

    constructor(data: List<StoryCommentBean>) {
        this.data = data
    }

    constructor(code: Int, message: String, data: List<StoryCommentBean>) : super(code, message) {
        this.data = data
    }
}
