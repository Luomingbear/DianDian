package xyz.toofun.diandian.bean

/**
 * 创建故事的返回值
 * Created by bear on 2017/5/11.
 */

class OnlyDataResponseBean : BaseResponseBean {
    var data: String? = null

    constructor() {}

    constructor(code: Int, message: String) : super(code, message) {}

    constructor(data: String) {
        this.data = data
    }

    constructor(code: Int, message: String, data: String) : super(code, message) {
        this.data = data
    }
}
