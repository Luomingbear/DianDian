package xyz.toofun.diandian.bean.issueStory

import com.amap.api.maps.model.LatLng

import xyz.toofun.diandian.bean.CardInfo

/**
 * 发布动态的基本数据信息
 * Created by bear on 2017/6/20.
 */

open class BaseIssueStoryBean {
    var userId: Int = 0 //用户id
    var content: String? = null //故事的内容
    var cityName: String? = null //城市名
    var locationTitle: String? = null //地点描述
    var latitude: Double = 0.toDouble()
    var longitude: Double = 0.toDouble()
    var createTime: String? = null //发布时间
    var destroyTime: String? = null //保存时间
    var isAnonymous: Boolean? = false

    var storyType = CardInfo.STORY

    constructor() {}

    constructor(userId: Int, content: String, cityName: String, locationTitle: String, latitude: Double,
                longitude: Double, createTime: String, destroyTime: String, isAnonymous: Boolean?, storyType: Int) {
        this.userId = userId
        this.content = content
        this.cityName = cityName
        this.locationTitle = locationTitle
        this.latitude = latitude
        this.longitude = longitude
        this.createTime = createTime
        this.destroyTime = destroyTime
        this.isAnonymous = isAnonymous
        this.storyType = storyType
    }

    fun setLatLng(latLng: LatLng) {
        this.latitude = latLng.latitude
        this.longitude = latLng.longitude
    }
}
