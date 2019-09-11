package com.xinzy.kotlin.wan.biz.main.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.alibaba.android.arouter.launcher.ARouter
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.biz.main.model.NavigationHomeModel
import com.xinzy.kotlin.wan.entity.Banner
import com.xinzy.kotlin.wan.entity.Topic
import com.xinzy.kotlin.wan.util.KEY_TITLE
import com.xinzy.kotlin.wan.util.KEY_URL
import com.xinzy.kotlin.wan.util.ROUTER_WEB
import com.xinzy.kotlin.wan.widget.Status
import com.xinzy.mvvm.lib.kotlin.base.BaseViewModel
import com.xinzy.mvvm.lib.kotlin.util.applyUI
import com.xinzy.mvvm.lib.kotlin.view.widget.BannerView
import com.xinzy.mvvm.lib.kotlin.view.widget.ItemBinding
import com.xinzy.mvvm.lib.kotlin.view.widget.MultiAdapter

class NavigationHomeViewModel(application: Application) : BaseViewModel<NavigationHomeModel>(application) {

    val homeAdapter = MultiAdapter(HomeItemBinding(this))

    val displayStatus = ObservableField<Status>(Status.Normal)
    val isShowRefreshing = ObservableBoolean(false)

    private var mPage: Int = 0
    private var mMaxPage = Int.MAX_VALUE
    private var isLoading: Boolean = false

    override fun onCreate() {
        start()
    }

    @SuppressLint("CheckResult")
    fun start() {
        isLoading = true
        isShowRefreshing.set(true)
        mModel!!.homePage().subscribe({ result ->
            mPage = 1
            isLoading = false
            isShowRefreshing.set(false)
            if (result.isEmpty()) {
                displayStatus.set(Status.Empty)
            } else {
                displayStatus.set(Status.Normal)
                homeAdapter.replace(result)
            }
        }, {
            isLoading = false
            isShowRefreshing.set(false)
            displayStatus.set(Status.Error)
        })
    }

    /** 加载下一页数据  */
    @SuppressLint("CheckResult")
    fun onNextPage() {
        if (mPage >= mMaxPage) return
        if (isLoading) return
        isLoading = true

        mModel!!.getHomeTopic(mPage).applyUI().subscribe({ results ->
            mPage++
            mMaxPage = results.getData().pageCount
            isLoading = false
            val topics = results.getData().getDatas()
            homeAdapter.addAll(topics)
        }, { isLoading = false })
    }

    fun onItemTopicClick(any: Any, position: Int) {
        (any as? Topic)?.let {
            ARouter.getInstance().build(ROUTER_WEB).withString(KEY_TITLE, it.displayTitle).withString(KEY_URL, it.link).navigation()
        }
    }

    fun onBannerItemClick(banner: BannerView, item: Any, position: Int) {
        (item as? Banner)?.let {
            ARouter.getInstance().build(ROUTER_WEB).withString(KEY_TITLE, it.title).withString(KEY_URL, it.url).navigation()
        }
    }

    inner class HomeItemBinding(vm: BaseViewModel<*>) : ItemBinding(viewModel = vm) {

        override fun getItemViewType(any: Any): Int {
            return if (any is List<*>) {
                R.layout.item_banner
            } else {
                R.layout.item_topic
            }
        }
    }

}