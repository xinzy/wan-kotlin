package com.xinzy.kotlin.wan.api

import com.xinzy.kotlin.wan.entity.*
import com.xinzy.mvvm.lib.kotlin.base.BaseModel
import com.xinzy.mvvm.lib.kotlin.util.applyUI

import io.reactivex.Observable

open class WanApiModel : BaseModel() {

    protected var mApi: WanApi = getService(WanApi::class)

    /**
     * 首页文章列表
     */
    fun getHomeTopic(page: Int): Observable<ApiResult<WanList<Topic>>> = mApi.homeArticles(page).applyUI()

    /**
     * 置顶文章
     */
    fun topTopic(): Observable<ApiResult<List<Topic>>> = mApi.topTopic().applyUI()

    /**
     * 首页banner
     */
    fun banner(): Observable<ApiResult<List<Banner>>> = mApi.banner().applyUI()

    /**
     * 知识体系分类
     */
    fun chapters(): Observable<ApiResult<List<Chapter>>> = mApi.chapters().applyUI()

    /**
     * 根据chapter id 查找文章列表
     */
    fun topicByChapterId(page: Int, cid: Int): Observable<ApiResult<WanList<Topic>>> =
            mApi.topicByChapterId(page, cid).applyUI()

    /**
     * 微信公众号列表
     */
    fun weixin(): Observable<ApiResult<List<Chapter>>> = mApi.weixin().applyUI()

    /**
     * 微信公众号下的文章列表
     */
    fun topicByWeixin(page: Int, cid: Int, keyword: String?): Observable<ApiResult<WanList<Topic>>> =
            mApi.topicByWeixin(page, cid, keyword).applyUI()

    /**
     * 项目分类列表
     */
    fun projectChapters(): Observable<List<Chapter>> =
            mApi.projectChapters().applyUI().map { it.getData() }

    /**
     * 项目分类下的文章列表
     */
    fun topicByProject(page: Int, cid: Int): Observable<ApiResult<WanList<Topic>>> =
            mApi.topicByProject(page, cid).applyUI()

    /**
     * 导航数据
     */
    fun navigation(): Observable<ApiResult<List<Navigation>>> = mApi.navigation().applyUI()


    //////////////////////////////////////////////////////////////////////////////////////////
    // 用户模块
    //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 登录
     */
    fun login(username: String, password: String): Observable<ApiResult<User>> =
            mApi.login(username, password).applyUI()

    /**
     * 注册
     */
    fun register(username: String, password: String, repassword: String): Observable<ApiResult<User>> =
            mApi.register(username, password, repassword).applyUI()
}
