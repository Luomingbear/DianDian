package xyz.toofun.diandian.bean.checkForUpdate

/**
 * 软件的更新信息
 * Created by bear on 2017/5/13.
 */

class AppUpdateBean {
    var version: Float = 0.toFloat()
    var title: String? = null
    var description: String? = null
    var path: String? = null

    constructor() {}

    constructor(version: Float, title: String, description: String, path: String) {
        this.version = version
        this.title = title
        this.description = description
        this.path = path
    }
}
