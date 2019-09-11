package com.xinzy.mvvm.lib.kotlin.base

import androidx.databinding.BaseObservable
import com.xinzy.mvvm.lib.kotlin.network.getRetrofitApi
import kotlin.reflect.KClass

open class BaseModel : BaseObservable() {

    protected fun <I> getService(clazz: KClass<*>): I {
        return getRetrofitApi(clazz)
    }

    open fun onCleared() {}
}