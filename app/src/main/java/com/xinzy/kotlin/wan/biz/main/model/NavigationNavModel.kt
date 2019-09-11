package com.xinzy.kotlin.wan.biz.main.model

import com.xinzy.kotlin.wan.api.WanApiModel
import com.xinzy.kotlin.wan.entity.Navigation
import io.reactivex.Observable

class NavigationNavModel : WanApiModel() {

    fun getNavigation(): Observable<List<Navigation>> {
        return navigation().map { it.getData() }
    }
}