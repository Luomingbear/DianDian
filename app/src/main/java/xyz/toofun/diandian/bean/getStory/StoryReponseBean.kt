package xyz.toofun.diandian.bean.getStory


import xyz.toofun.diandian.bean.BaseResponseBean

/**
 * 获取故事详情的时候的返回值
 * Created by bear on 2017/5/12.
 */

class StoryReponseBean : BaseResponseBean {
    var data: StoryBean? = null

    constructor() {}

    constructor(code: Int, message: String) : super(code, message) {}

    constructor(data: StoryBean) {
        this.data = data
    }

    constructor(code: Int, message: String, data: StoryBean) : super(code, message) {
        this.data = data
    }
}
