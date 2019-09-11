package com.xinzy.kotlin.wan.biz.main.model

import android.annotation.SuppressLint
import com.xinzy.kotlin.wan.api.WanApiModel
import com.xinzy.kotlin.wan.entity.ApiResult
import com.xinzy.kotlin.wan.entity.Banner
import com.xinzy.kotlin.wan.entity.Topic
import com.xinzy.kotlin.wan.entity.WanList
import com.xinzy.mvvm.lib.kotlin.util.applyUI
import com.xinzy.mvvm.lib.kotlin.view.widget.ItemBinding
import com.xinzy.mvvm.lib.kotlin.view.widget.MultiAdapter
import io.reactivex.Observable
import io.reactivex.functions.Function3
import java.util.ArrayList

class NavigationHomeModel : WanApiModel() {

    @SuppressLint("CheckResult")
    fun homePage(): Observable<List<Any>> {
        val banner = banner()
        val hot = topTopic()
        val topics = getHomeTopic(0)

        return Observable.zip(banner, hot, topics,
            Function3<ApiResult<List<Banner>>, ApiResult<List<Topic>>, ApiResult<WanList<Topic>>, List<Any>> { t1, t2, t3 ->
                mutableListOf<Any>().apply {
                    add(t1.getData())
                    addAll(t2.getData())
                    addAll(t3.getData().getDatas())
                }
            }).applyUI()
    }
}