package com.xinzy.kotlin.wan.biz.chapter.viewmodel

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
import com.xinzy.mvvm.lib.kotlin.view.widget.ItemBinding
import com.xinzy.mvvm.lib.kotlin.view.widget.MultiAdapter

class TopicViewModel(application: Application, private val mChapter: Chapter)
    : BaseViewModel<WanApiModel>(application) {

    val isRefreshing = ObservableBoolean()
    val displayStatus = ObservableField<Status>(Status.Normal)

    var topicAdapter = MultiAdapter(ItemBinding(R.layout.item_topic, this))

    private var mPage = 0
    private var mMaxPage = Integer.MAX_VALUE

    private var isLoading: Boolean = false

    fun onItemTopicClick(any: Any, position: Int) {
        (any as? Topic)?.let {
            ARouter.getInstance().build(ROUTER_WEB).withString(KEY_TITLE, it.displayTitle).withString(KEY_URL, it.link).navigation()
        }
    }

    @SuppressLint("CheckResult")
    private fun load(page: Int) {
        if (isLoading) return
        if (page >= mMaxPage) return

        val isFirstPage = page == 0
        if (isFirstPage) isRefreshing.set(true)

        mModel!!.topicByChapterId(page, mChapter.id).subscribe({ result ->
            isLoading = false
            isRefreshing.set(false)
            if (result.isSuccess()) {

                val wanList = result.getData()
                mMaxPage = wanList.pageCount
                mPage = wanList.page
                val topics = wanList.getDatas()

                if (topics.isNotEmpty()) {
                    if (isFirstPage) topicAdapter.replace(topics)
                    else topicAdapter.addAll(topics)
                } else {
                    if (isFirstPage) displayStatus.set(Status.Empty)
                }
            } else {
                if (isFirstPage) displayStatus.set(Status.Error)
            }
        }, {
            isLoading = false
            isRefreshing.set(false)
            if (isFirstPage) displayStatus.set(Status.Error)
        })
    }

    fun onNextPage() {
        load(mPage)
    }

    fun refresh() {
        load(0)
    }

    override fun onCreate() {
        load(0)
    }
}