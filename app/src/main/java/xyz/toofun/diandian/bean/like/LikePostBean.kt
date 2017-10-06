package xyz.toofun.diandian.bean.like

/**
 * 喜欢和不喜欢上传的数据
 * Created by bear on 2017/5/14.
 */

class LikePostBean {
    var storyId: String? = null
    var userId: Int = 0
    var createTime: String? = null
    var sure: Boolean? = null

    constructor() {}

    constructor(storyId: String, userId: Int, createTime: String, sure: Boolean?) {
        this.storyId = storyId
        this.userId = userId
        this.createTime = createTime
        this.sure = sure
    }
}
