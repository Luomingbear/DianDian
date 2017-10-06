package xyz.toofun.diandian.bean.getStory

import xyz.toofun.diandian.bean.BaseResponseBean
import xyz.toofun.diandian.bean.CardInfo

/**
 * 获取附近的故事时返回的值
 * Created by bear on 2017/5/12.
 */

class NearStoriesRsponseBean : BaseResponseBean {
    var data: List<CardInfo>? = null

    constructor() {}

    constructor(code: Int, message: String) : super(code, message) {}

    constructor(data: List<CardInfo>) {
        this.data = data
    }

    constructor(code: Int, message: String, data: List<CardInfo>) : super(code, message) {
        this.data = data
    }
}
