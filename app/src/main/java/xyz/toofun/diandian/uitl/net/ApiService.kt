package xyz.toofun.diandian.uitl.net

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import xyz.toofun.diandian.bean.*
import xyz.toofun.diandian.bean.checkForUpdate.VersionResponseBean
import xyz.toofun.diandian.bean.comment.CommentPostBean
import xyz.toofun.diandian.bean.comment.CommentReponseBean
import xyz.toofun.diandian.bean.comment.CommentSizeResponseBean
import xyz.toofun.diandian.bean.comment.ReplyPostBean
import xyz.toofun.diandian.bean.getStory.*
import xyz.toofun.diandian.bean.issueStory.IssueLongStoryBean
import xyz.toofun.diandian.bean.issueStory.IssueStoryBean
import xyz.toofun.diandian.bean.like.LikePostBean
import xyz.toofun.diandian.bean.message.StoryCommentResponseBean
import xyz.toofun.diandian.bean.message.StoryLikeResponseBean
import xyz.toofun.diandian.bean.read.ReadCommentPostBean
import xyz.toofun.diandian.bean.read.ReadStoryLikePostBean
import xyz.toofun.diandian.bean.user.RegisterResponseBean
import xyz.toofun.diandian.bean.user.UserIdBean
import xyz.toofun.diandian.bean.user.UserLoginResponseBean

/**
 * 接口方法
 * Created by bear on 2017/5/8.
 */

interface ApiService {
    /**
     * 获取启动页的数据
     *
     * @return LauncherResponseBean
     */
    @get:GET("getLauncher.php")
    val launcher: Call<LauncherResponseBean>

    /**
     * 注册账号
     *
     * @param userInfo 账户信息
     * @return RegisterResponseBean
     */
    @POST("registerUser.php")
    fun register(@Body userInfo: RegisterUserBean): Call<RegisterResponseBean>


    /**
     * 获取上传图片需要的token
     *
     * @return getQiniuToken
     */
    @get:GET("getGetQiniuToken.php")
    val getQiniuToken: Call<TokenResponseBean>

    /**
     * 登录账号
     *
     * @param loginInfo
     * @return
     */
    @POST("loginUser.php")
    fun login(@Body loginInfo: LoginBean): Call<UserLoginResponseBean>

    /**
     * 获取用户的基本信息
     *
     * @param userIdBean
     * @return
     */
    @POST("getUserInfo.php")
    fun getUserInfo(@Body userIdBean: UserIdBean): Call<UserLoginResponseBean>

    /**
     * 新建短文
     *
     * @param issueStoryBean
     * @return
     */
    @POST("issueStory.php")
    fun issueStory(@Body issueStoryBean: IssueStoryBean): Call<OnlyDataResponseBean>

    /**
     * 新建文章
     *
     * @param longStoryBean
     * @return
     */
    @POST("issueLongStory.php")
    fun issueLongStory(@Body longStoryBean: IssueLongStoryBean): Call<OnlyDataResponseBean>

    /**
     * 获取故事的详细信息
     *
     * @param storyIdBean 包含故事id的请求体
     * @return
     */
    @POST("getStoryInfo.php")
    fun getStoryInfo(@Body storyIdBean: StoryIdBean): Call<StoryReponseBean>

    /**
     * 获取用户附近未过期的故事
     *
     * @param locationBean
     * @return
     */
    @POST("getNearStory.php")
    fun getNearStory(@Body locationBean: LocationBean): Call<NearStoriesRsponseBean>

    /**
     * 获取更新信息
     *
     * @return
     */
    @GET("checkForUpdate.php")
    fun checkForUpdate(): Call<VersionResponseBean>

    /**
     * 喜欢和不喜欢的接口
     *
     * @param likePostBean
     * @return
     */
    @POST("likeStory.php")
    fun likeStory(@Body likePostBean: LikePostBean): Call<OnlyDataResponseBean>

    /**
     * 发表评论
     *
     * @param commentPostBean
     * @return
     */
    @POST("issueComment.php")
    fun issueComment(@Body commentPostBean: CommentPostBean): Call<OnlyDataResponseBean>

    /**
     * 回复对象的评论
     *
     * @param replyPostBean
     * @return
     */
    @POST("replyComment.php")
    fun replyComment(@Body replyPostBean: ReplyPostBean): Call<OnlyDataResponseBean>

    /**
     * 获取热门评论数据
     *
     * @param storyIdBean
     * @return
     */
    @POST("getHotComment.php")
    fun getHotComment(@Body storyIdBean: StoryIdBean): Call<CommentReponseBean>

    /**
     * 获取最新的评论 返回10条数据
     *
     * @param storyIdBean
     * @return
     */
    @POST("getNestComment.php")
    fun getNestComment(@Body storyIdBean: StoryIdBean): Call<CommentReponseBean>

    /**
     * 获取评论的数量
     *
     * @param storyIdBean
     * @return
     */
    @POST("getCommentSize.php")
    fun getCommentSize(@Body storyIdBean: StoryIdBean): Call<CommentSizeResponseBean>

    /**
     * 获取推荐的故事
     *
     * @param recommendPostBean
     * @return
     */
    @POST("getRecommendStory.php")
    fun getRecommendStory(@Body recommendPostBean: RecommendPostBean): Call<NearStoriesRsponseBean>

    /**
     * 获取用户的故事
     *
     * @param userStoryPostBean
     * @return
     */
    @POST("getUserStory.php")
    fun getUserStory(@Body userStoryPostBean: UserStoryPostBean): Call<UserStoryResponseBean>

    /**
     * 获取故事配图
     *
     * @param storyIdBean
     * @return
     */
    @POST("getStoryPic.php")
    fun getStoryPic(@Body storyIdBean: StoryIdBean): Call<StoryPicResponseBean>

    /**
     * 获取用户的故事收到的赞
     *
     * @param userIdBean
     * @return
     */
    @POST("getStoryLike.php")
    fun getStoryLike(@Body userIdBean: UserIdBean): Call<StoryLikeResponseBean>

    /**
     * 获取用户的故事评论
     *
     * @param userIdBean
     * @return
     */
    @POST("getStoryComment.php")
    fun getStoryComment(@Body userIdBean: UserIdBean): Call<StoryCommentResponseBean>

    /**
     * 标记故事赞为已读
     *
     * @param readStoryLikePostBean
     * @return
     */
    @POST("updateStoryLikeRead.php")
    fun updateStoryLikeRead(@Body readStoryLikePostBean: ReadStoryLikePostBean): Call<OnlyDataResponseBean>

    /**
     * 标记收到的评论为已读
     *
     * @param readCommentPostBean
     * @return
     */
    @POST("updateStoryCommentRead.php")
    fun updateStoryCommentRead(@Body readCommentPostBean: ReadCommentPostBean): Call<OnlyDataResponseBean>

    /**
     * 获取与自己相关的未读信息数目
     *
     * @param userIdBean
     * @return
     */
    @POST("getUnReadNum.php")
    fun getUnreadNum(@Body userIdBean: UserIdBean): Call<OnlyDataResponseBean>

    /**
     * 上传用户的clientId用于个推推送
     *
     * @param clientIdBean
     * @return
     */
    @POST("uploadClientID.php")
    fun uploadClientId(@Body clientIdBean: ClientIdBean): Call<OnlyDataResponseBean>


}
