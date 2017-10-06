package xyz.toofun.diandian.bean.read

/**
 * 标记故事点赞为已读的上传数据
 * Created by bear on 2017/5/25.
 */

class ReadStoryLikePostBean {
    var readInfo: List<String>? = null

    constructor() {}

    constructor(readInfo: List<String>) {
        this.readInfo = readInfo
    }
}
