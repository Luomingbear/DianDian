package xyz.toofun.diandian.bean.getStory

/**
 * 请求附近的故事的时候上传的数据
 * Created by bear on 2017/5/12.
 */

class LocationBean {
    var userId: Int = 0
    var latitude: Double = 0.toDouble()
    var longitude: Double = 0.toDouble()
    var scale = 17

    constructor() {}

    constructor(userId: Int, latitude: Double, longitude: Double, scale: Int) {
        this.userId = userId
        this.latitude = latitude
        this.longitude = longitude
        this.scale = scale
    }
}
