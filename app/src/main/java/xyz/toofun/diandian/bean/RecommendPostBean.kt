package xyz.toofun.diandian.bean

/**
 * 获取推荐的故事时的发送值
 * Created by bear on 2017/5/20.
 */

class RecommendPostBean {
    var userId: Int = 0
    var cityName: String? = null

    constructor() {}

    constructor(userId: Int, cityName: String) {
        this.userId = userId
        this.cityName = cityName
    }
}
