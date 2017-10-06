package xyz.toofun.diandian.bean

/**
 * Created by bear on 2017/7/8.
 */

class ClientIdBean {
    var clientId: String? = null
    var userId: Int = 0

    constructor() {

    }

    constructor(clientId: String, userId: Int) {
        this.clientId = clientId
        this.userId = userId
    }
}
