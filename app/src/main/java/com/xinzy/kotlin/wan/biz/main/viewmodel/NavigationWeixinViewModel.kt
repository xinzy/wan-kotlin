package com.xinzy.kotlin.wan.biz.main.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.xinzy.kotlin.wan.api.WanApiModel
import com.xinzy.kotlin.wan.entity.Chapter
import com.xinzy.mvvm.lib.kotlin.base.BaseViewModel

class NavigationWeixinViewModel(application: Application) : BaseViewModel<WanApiModel>(application) {
    var weixinListEvent = MutableLiveData<List<Chapter>>()

    override fun onCreate() {
        start()
    }

    @SuppressLint("CheckResult")
    private fun start() {
        showProgress()
        mModel!!.weixin().map { it.getData() }.subscribe {
            dismissProgress()
            weixinListEvent.postValue(it)
        }
    }
}