package xyz.toofun.diandian.bean.checkForUpdate


import xyz.toofun.diandian.bean.BaseResponseBean

/**
 * 获取最新的版本信息返回值
 * Created by bear on 2017/5/13.
 */

class VersionResponseBean : BaseResponseBean {
    var data: AppUpdateBean? = null

    constructor() {}

    constructor(code: Int, message: String) : super(code, message) {}

    constructor(data: AppUpdateBean) {
        this.data = data
    }

    constructor(code: Int, message: String, data: AppUpdateBean) : super(code, message) {
        this.data = data
    }
}
