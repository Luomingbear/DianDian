package xyz.toofun.diandian.bean.message


import xyz.toofun.diandian.bean.BaseResponseBean

/**
 * 获取故事的赞的数据返回值
 * Created by bear on 2017/5/24.
 */

class StoryLikeResponseBean : BaseResponseBean {
    var data: List<StoryLikeBean>? = null

    constructor() {}

    constructor(code: Int, message: String) : super(code, message) {}

    constructor(data: List<StoryLikeBean>) {
        this.data = data
    }

    constructor(code: Int, message: String, data: List<StoryLikeBean>) : super(code, message) {
        this.data = data
    }
}
