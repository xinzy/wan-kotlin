package com.xinzy.kotlin.wan.biz.main.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.xinzy.kotlin.wan.api.WanApiModel
import com.xinzy.kotlin.wan.entity.Chapter
import com.xinzy.mvvm.lib.kotlin.base.BaseViewModel

class NavigationProjectViewModel(application: Application) : BaseViewModel<WanApiModel>(application) {

    val projectChapters: MutableLiveData<List<Chapter>> = MutableLiveData()

    @SuppressLint("CheckResult")
    private fun start() {
        mModel!!.projectChapters().subscribe { chapters -> projectChapters.postValue(chapters)}
    }

    override fun onCreate() {
        start()
    }
}