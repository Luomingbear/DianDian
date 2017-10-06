package xyz.toofun.diandian.bean.user


import xyz.toofun.diandian.bean.BaseUserInfo

/**
 * 用户基本信息
 * Created by bear on 2017/5/10.
 */

class UserLoginResponseBean {
    var code: Int = 0
    var message: String? = null
    var data: BaseUserInfo? = null

    constructor() {}

    constructor(code: Int, message: String, data: BaseUserInfo) {
        this.code = code
        this.message = message
        this.data = data
    }
}
