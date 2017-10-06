package xyz.toofun.diandian.bean

/**
 * Created by bear on 2017/4/26.
 */

class LoginBean {
    var email: String? = null
    var password: String? = null

    constructor() {}

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }
}
