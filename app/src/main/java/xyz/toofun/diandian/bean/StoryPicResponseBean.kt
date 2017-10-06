package xyz.toofun.diandian.bean

/**
 * 获取故事配图的返回值
 * Created by bear on 2017/5/24.
 */

class StoryPicResponseBean : BaseResponseBean {
    var data: List<String>? = null

    constructor() {}

    constructor(code: Int, message: String) : super(code, message) {}

    constructor(data: List<String>) {
        this.data = data
    }

    constructor(code: Int, message: String, data: List<String>) : super(code, message) {
        this.data = data
    }
}
