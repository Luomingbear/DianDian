package xyz.toofun.diandian.bean.getStory

/**
 * 请求用户的故事的请求数据
 * Created by bear on 2017/5/23.
 */

class UserStoryPostBean {
    var userId: Int = 0
    var page = 1 //第几页，用于加载更多

    constructor() {}

    constructor(userId: Int, start: Int) {
        this.userId = userId
        this.page = start
    }
}
