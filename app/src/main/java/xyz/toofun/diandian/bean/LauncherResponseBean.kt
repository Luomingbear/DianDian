package xyz.toofun.diandian.bean

/**
 * 启动页的数据格式
 * Created by bear on 2017/5/8.
 */

class LauncherResponseBean {
    var describe: String? = null //描述文本
    var url: String? = null //描述文本
    var ad: String? = null //描述文本

    constructor() {}

    constructor(describe: String, url: String, ad: String) {
        this.describe = describe
        this.url = url
        this.ad = ad
    }
}
