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
import com.xinzy.mvvm.lib.kotlin.view.widget.ItemBinding
import com.xinzy.mvvm.lib.kotlin.view.widget.MultiAdapter

class ProjectViewModel(application: Application, private  val mChapter: Chapter) : BaseViewModel<WanApiModel>(application) {

    val isRefreshing = ObservableBoolean()
    val displayStatus = ObservableField<Status>(Status.Normal)

    var projectAdapter = MultiAdapter(ItemBinding(R.layout.item_project, this))

    private var mPage: Int = 0
    private var mMaxPage = Integer.MAX_VALUE

    fun onItemTopicClick(any: Any, position: Int) {
        (any as? Topic)?.let {
            ARouter.getInstance().build(ROUTER_WEB).withString(KEY_TITLE, it.displayTitle)
                .withString(KEY_URL, it.projectLink).navigation()
        }
    }

    @SuppressLint("CheckResult")
    private fun load(page: Int) {

        if (page >= mMaxPage) return
        val isFirstPage = page == 0
        if (isFirstPage) isRefreshing.set(true)

        mModel!!.topicByProject(page, mChapter.id).subscribe({ result ->
            isRefreshing.set(false)
            if (result.isSuccess()) {

                val wanList = result.getData()
                mMaxPage = wanList.pageCount
                val topics = wanList.getDatas()

                if (topics.isNotEmpty()) {
                    if (isFirstPage) {
                        projectAdapter.replace(topics)
                        displayStatus.set(Status.Normal)
                    } else {
                        projectAdapter.addAll(topics)
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
        load(mPage)
    }

    fun refresh() {
        load(0)
    }

    override fun onCreate() {
        load(0)
    }
}