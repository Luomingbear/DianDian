package xyz.toofun.diandian.bean.issueStory

/**
 * 发布的文章的数据结构
 * Created by bear on 2017/6/20.
 */

class IssueLongStoryBean : BaseIssueStoryBean {

    var title: String? = null //标题

    constructor() {}

    constructor(title: String) {
        this.title = title
    }

    constructor(userId: Int, content: String, cityName: String, locationTitle: String, latitude: Double, longitude: Double, createTime: String, destroyTime: String, isAnonymous: Boolean?, storyType: Int, title: String) : super(userId, content, cityName, locationTitle, latitude, longitude, createTime, destroyTime, isAnonymous, storyType) {
        this.title = title
    }
}

