package xyz.toofun.diandian.bean.user

/**
 * 存放用户的id，用于搜索用户信息
 * Created by bear on 2017/5/11.
 */

class UserIdBean {
    var userId: Int = 0

    constructor() {}

    constructor(userId: Int) {
        this.userId = userId
    }
}
