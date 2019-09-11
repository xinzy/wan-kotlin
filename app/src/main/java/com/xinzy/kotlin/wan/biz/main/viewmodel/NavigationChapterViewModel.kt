package com.xinzy.kotlin.wan.biz.main.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.alibaba.android.arouter.launcher.ARouter
import com.xinzy.kotlin.wan.api.WanApiModel
import com.xinzy.kotlin.wan.entity.Chapter
import com.xinzy.kotlin.wan.util.KEY_CHAPTER
import com.xinzy.kotlin.wan.util.ROUTER_TOPICS
import com.xinzy.kotlin.wan.widget.Status
import com.xinzy.mvvm.lib.kotlin.base.BaseViewModel
import com.xinzy.mvvm.lib.kotlin.util.logV

class NavigationChapterViewModel(application: Application) : BaseViewModel<WanApiModel>(application) {

    var isRefreshing = ObservableBoolean(false)
    var displayStatus = ObservableField<Status>(Status.Normal)

    var chapterList = ObservableField<List<Chapter>>()

    @SuppressLint("CheckResult")
    fun start() {
        isRefreshing.set(true)
        mModel!!.chapters().subscribe({ result ->
            isRefreshing.set(false)
            if (result.isSuccess()) {
                val chapters = result.getData()
                if (chapters.isNotEmpty()) {
                    displayStatus.set(Status.Normal)
                    chapterList.set(chapters)
                } else {
                    displayStatus.set(Status.Empty)
                }
            } else {
                displayStatus.set(Status.Error)
            }
        }, { e -> displayStatus.set(Status.Error) })
    }

    fun onItemChapterClick(any: Any, position: Int) {
        (any as? Chapter)?.let {
            ARouter.getInstance().build(ROUTER_TOPICS).withParcelable(KEY_CHAPTER, it).navigation()
        }
    }

    fun onItemTagClick(tag: String) {
        logV("onItemTagClick: $tag")
    }

    override fun onCreate() {
        start()
    }
}