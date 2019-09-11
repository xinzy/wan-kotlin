package com.xinzy.kotlin.wan.biz.main.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.alibaba.android.arouter.launcher.ARouter
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.api.WanApiModel
import com.xinzy.kotlin.wan.entity.Chapter
import com.xinzy.kotlin.wan.entity.Topic
import com.xinzy.kotlin.wan.util.KEY_TITLE
import com.xinzy.kotlin.wan.util.KEY_URL
import com.xinzy.kotlin.wan.util.ROUTER_WEB
import com.xinzy.kotlin.wan.widget.Status
import com.xinzy.mvvm.lib.kotlin.base.BaseViewModel
import com.xinzy.mvvm.lib.kotlin.util.logD
import com.xinzy.mvvm.lib.kotlin.view.widget.ItemBinding
import com.xinzy.mvvm.lib.kotlin.view.widget.MultiAdapter

class WeixinViewModel(application: Application, private val mChapter: Chapter) : BaseViewModel<WanApiModel>(application) {
    private val TEMPLATE_HINT = "在%s公众号中搜索"

    val isRefreshing = ObservableBoolean()
    val displayStatus = ObservableField<Status>(Status.Normal)
    val searchHint = ObservableField<String>()

    var topicAdapter = MultiAdapter(ItemBinding(R.layout.item_topic, this))

    private var searchKeyword: String? = null
    private var mPage: Int = 0
    private var mMaxPage = Integer.MAX_VALUE
    
    init {
        searchHint.set(String.format(TEMPLATE_HINT, mChapter.getName()))
    }

    fun onItemTopicClick(`object`: Any, position: Int) {
        val topic = `object` as Topic
        ARouter.getInstance().build(ROUTER_WEB).withString(KEY_TITLE, topic.displayTitle).withString(KEY_URL, topic.link).navigation()
    }

    @SuppressLint("CheckResult")
    private fun load(page: Int, keyword: String?) {

        if (page >= mMaxPage) return
        val isFirstPage = page == 0
        if (isFirstPage) isRefreshing.set(true)

        mModel!!.topicByWeixin(page, mChapter.id, keyword).subscribe({ result ->
            isRefreshing.set(false)
            if (result.isSuccess()) {

                val wanList = result.getData()
                mMaxPage = wanList.pageCount
                val topics = wanList.getDatas()

                if (topics.isNotEmpty()) {
                    if (isFirstPage) {
                        topicAdapter.replace(topics)
                        displayStatus.set(Status.Normal)
                    } else {
                        topicAdapter.addAll(topics)
                    }
                } else {
                    if (isFirstPage) displayStatus.set(Status.Empty)
                }
                mPage++
            } else {
                if (isFirstPage) displayStatus.set(Status.Error)
            }
        }, {
            isRefreshing.set(false)
            if (isFirstPage) displayStatus.set(Status.Error)
        })
    }

    fun onNextPage() {
        load(mPage, searchKeyword)
    }

    fun refresh() {
        mMaxPage = Int.MAX_VALUE
        load(0, searchKeyword)
    }

    fun onSearch(keyword: String) {
        mMaxPage = Int.MAX_VALUE
        searchKeyword = keyword
        load(0, keyword)
        logD("search text: $keyword")
    }

    override fun onCreate() {
        load(0, searchKeyword)
    }
}