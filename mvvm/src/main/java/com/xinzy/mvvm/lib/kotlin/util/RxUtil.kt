package com.xinzy.mvvm.lib.kotlin.util

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.applyUI() = this.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())!!
