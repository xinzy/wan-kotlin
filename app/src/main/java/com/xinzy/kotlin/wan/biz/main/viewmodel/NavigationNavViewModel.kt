package com.xinzy.kotlin.wan.biz.main.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.biz.main.model.NavigationNavModel
import com.xinzy.kotlin.wan.entity.Navigation
import com.xinzy.kotlin.wan.entity.Topic
import com.xinzy.kotlin.wan.util.KEY_TITLE
import com.xinzy.kotlin.wan.util.KEY_URL
import com.xinzy.kotlin.wan.util.ROUTER_WEB
import com.xinzy.mvvm.lib.kotlin.base.BaseViewModel
import com.xinzy.mvvm.lib.kotlin.event.SingleLiveEvent
import com.xinzy.mvvm.lib.kotlin.util.logD
import com.xinzy.mvvm.lib.kotlin.view.widget.ItemBinding
import com.xinzy.mvvm.lib.kotlin.view.widget.MultiAdapter

class NavigationNavViewModel(application: Application) : BaseViewModel<NavigationNavModel>(application) {

    val categoryAdapter = MultiAdapter(ItemBinding(R.layout.item_nav_cat, this))
    val itemAdapter = MultiAdapter(ItemBinding(R.layout.item_nav_detail, this))

    val navigations = ObservableField<List<Navigation>>()

    val itemNavigationPosition = SingleLiveEvent<Int>()
    val itemNavigationScrollPosition = SingleLiveEvent<Int>()
    val onNavigationScrollListener = OnNavigationScrollListener()

    private var mSelectedPosition = 0

    @SuppressLint("CheckResult")
    private fun start() {
        mModel!!.getNavigation().subscribe {
            navigations.set(it)
        }
    }

    fun isItemSelected(position: Int) = mSelectedPosition == position

    fun onItemNavigationClick(any: Any, position: Int) {
        if (position == mSelectedPosition) return
        logD("item navigation click: $any")
        select(position)
    }

    fun onItemArticleClick(any: Any, position: Int) {
        logD("item article click: $any")
        (any as? Topic)?.let {
            ARouter.getInstance().build(ROUTER_WEB).withString(KEY_TITLE, it.displayTitle).withString(KEY_URL, it.link)
                .navigation()
        }
    }

    private fun select(position: Int, scrollTo: Boolean = false) {
        val lastPosition = mSelectedPosition
        mSelectedPosition = position
        categoryAdapter.notifyItemChanged(lastPosition)
        categoryAdapter.notifyItemChanged(position)

        if (!scrollTo) itemNavigationPosition.postValue(position)
    }

    override fun onCreate() {
        start()
    }

    inner class OnNavigationScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState != RecyclerView.SCROLL_STATE_IDLE) return
            (recyclerView.layoutManager as? LinearLayoutManager)?.let {
                val index = it.findFirstVisibleItemPosition()
                select(index, true)
                itemNavigationScrollPosition.postValue(index)
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        }
    }
}