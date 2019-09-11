package com.xinzy.kotlin.wan.util

const val BASE_URL = "https://www.wanandroid.com"

///////////////////////////////////////////////////////////////////////////////////////////
/*
 * Keys
 */
///////////////////////////////////////////////////////////////////////////////////////////
const val KEY_URL = "URL"
const val KEY_TITLE = "TITLE"
const val KEY_CHAPTER = "CHAPTER"
const val KEY_CHAPTER_ID = "CHAPTER_ID"

///////////////////////////////////////////////////////////////////////////////////////////
/*
 * Router
 */
///////////////////////////////////////////////////////////////////////////////////////////
private const val BASE_PATH = "/xinzy/wan/"
private const val PATH_VIEW = BASE_PATH + "view/"

/**首页 */
const val ROUTER_MAIN = PATH_VIEW + "main"

/** 文章列表  */
const val ROUTER_TOPICS = PATH_VIEW + "topics"

/** WebView  */
const val ROUTER_WEB = PATH_VIEW + "web"

/** 注册 */
const val ROUTER_REGISTER = PATH_VIEW + "register"

/** 登录 */
const val ROUTER_LOGIN = PATH_VIEW + "login"

