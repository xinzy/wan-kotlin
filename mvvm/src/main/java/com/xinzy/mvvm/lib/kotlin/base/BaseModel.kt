package com.xinzy.mvvm.lib.kotlin.base

import androidx.databinding.BaseObservable
import com.xinzy.mvvm.lib.kotlin.network.getRetrofitApi
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

open class BaseModel : BaseObservable() {

    protected fun <I> getService(clazz: KClass<*>, converterFactory: Converter.Factory = GsonConverterFactory.create()): I {
        return getRetrofitApi(clazz, converterFactory)
    }

    open fun onCleared() {}
}