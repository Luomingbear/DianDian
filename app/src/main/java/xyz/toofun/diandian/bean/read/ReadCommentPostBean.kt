package xyz.toofun.diandian.bean.read

/**
 * 上传已读评论数据
 * Created by bear on 2017/5/25.
 */

class ReadCommentPostBean {
    var readInfo: List<String>? = null //commentId 列表

    constructor() {}

    constructor(readInfo: List<String>) {
        this.readInfo = readInfo
    }
}
