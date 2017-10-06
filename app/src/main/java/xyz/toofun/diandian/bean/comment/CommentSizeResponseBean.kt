package xyz.toofun.diandian.bean.comment


import xyz.toofun.diandian.bean.BaseResponseBean

/**
 * 获取评论数量
 * Created by bear on 2017/5/15.
 */

class CommentSizeResponseBean : BaseResponseBean {
    var data: Int = 0

    constructor() {}

    constructor(code: Int, message: String) : super(code, message) {}

    constructor(data: Int) {
        this.data = data
    }

    constructor(code: Int, message: String, data: Int) : super(code, message) {
        this.data = data
    }
}
