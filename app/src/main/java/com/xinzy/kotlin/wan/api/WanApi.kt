package com.xinzy.kotlin.wan.api

import com.xinzy.kotlin.wan.entity.*
import com.xinzy.mvvm.lib.kotlin.annotation.BaseUri
import com.xinzy.mvvm.lib.kotlin.annotation.HttpConfig

import io.reactivex.Observable

import com.xinzy.kotlin.wan.util.BASE_URL
import retrofit2.http.*

@HttpConfig(timeout = 5, unsafe = true)
@BaseUri(BASE_URL)
interface WanApi {

    /**
     * 首页文章列表
     */
    @GET("/article/list/{page}/json")
    fun homeArticles(@Path("page") page: Int): Observable<ApiResult<WanList<Topic>>>

    /**
     * 置顶文章
     */
    @GET("/article/top/json")
    fun topTopic(): Observable<ApiResult<List<Topic>>>

    /**
     * 首页banner
     */
    @GET("/banner/json")
    fun banner(): Observable<ApiResult<List<Banner>>>

    /**
     * 知识体系分类
     */
    @GET("/tree/json")
    fun chapters(): Observable<ApiResult<List<Chapter>>>

    /** 知识体系下的文章列表  */
    @GET("/article/list/{page}/json")
    fun topicByChapterId(@Path("page") page: Int, @Query("cid") cid: Int)
            : Observable<ApiResult<WanList<Topic>>>

    /** 微信公众号列表  */
    @GET("/wxarticle/chapters/json")
    fun weixin(): Observable<ApiResult<List<Chapter>>>

    /** 知识体系下的文章列表  */
    @GET("/wxarticle/list/{cid}/{page}/json")
    fun topicByWeixin(@Path("page") page: Int, @Path("cid") cid: Int,
                      @Query("k") keyword: String?): Observable<ApiResult<WanList<Topic>>>

    /** 项目分类列表  */
    @GET("/project/tree/json")
    fun projectChapters(): Observable<ApiResult<List<Chapter>>>

    /** 项目分类下的文章列表 */
    @GET("/project/list/{page}/json")
    fun topicByProject(@Path("page") page: Int, @Query("cid") cid: Int)
            : Observable<ApiResult<WanList<Topic>>>

    /** 导航数据 */
    @GET("/navi/json")
    fun navigation(): Observable<ApiResult<List<Navigation>>>


    //////////////////////////////////////////////////////////////////////////////////////////
    // 用户模块
    //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 用户登录
     */
    @POST("/user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String)
            : Observable<ApiResult<User>>
    /**
     * 用户登录
     */
    @POST("/user/register")
    @FormUrlEncoded
    fun register(@Field("username") username: String, @Field("password") password: String,
                 @Field("repassword") repassword: String): Observable<ApiResult<User>>

    /**
     * 退出
     */
    @GET("/user/logout/json")
    fun logout(): Observable<ApiResult<Any>>

    /**
     * 我的积分
     */
    @GET("/lg/coin/userinfo/json")
    fun coin(): Observable<ApiResult<Coin>>
}
