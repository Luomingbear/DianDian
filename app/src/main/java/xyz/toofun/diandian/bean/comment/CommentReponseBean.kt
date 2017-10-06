package xyz.toofun.diandian.bean.comment


import xyz.toofun.diandian.bean.BaseResponseBean

/**
 * 获取评论的返回值
 * Created by bear on 2017/5/15.
 */

class CommentReponseBean : BaseResponseBean {
    var data: List<CommentBean>? = null

    constructor() {}

    constructor(code: Int, message: String) : super(code, message) {}

    constructor(data: List<CommentBean>) {
        this.data = data
    }

    constructor(code: Int, message: String, data: List<CommentBean>) : super(code, message) {
        this.data = data
    }
}
