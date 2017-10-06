package xyz.toofun.diandian.bean

import xyz.toofun.diandian.uitl.net.CodeUtil

/**
 * 基本的返回数据
 * Created by bear on 2017/5/11.
 */

open class BaseResponseBean {
    var code = CodeUtil.Succeed
    var message = ""

    constructor() {}

    constructor(code: Int, message: String) {
        this.code = code
        this.message = message
    }
}
