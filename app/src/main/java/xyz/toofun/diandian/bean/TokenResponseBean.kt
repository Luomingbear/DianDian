package xyz.toofun.diandian.bean

/**
 * getQiniuToken
 * Created by bear on 2017/5/10.
 */

class TokenResponseBean {
    var code: Int = 0

    var message: String? = null

    var data: String? = null

    constructor() {}

    constructor(code: Int, message: String, data: String) {
        this.code = code
        this.message = message
        this.data = data
    }
}
