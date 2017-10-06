package xyz.toofun.diandian.bean.user


import xyz.toofun.diandian.bean.BaseUserInfo

/**
 * 注册返回值
 * Created by bear on 2017/5/10.
 */

class RegisterResponseBean {
    var code = 500
    var message: String? = null
    var data: BaseUserInfo? = null

    constructor() {}

    constructor(code: Int, message: String, data: BaseUserInfo) {
        this.code = code
        this.message = message
        this.data = data
    }
}
