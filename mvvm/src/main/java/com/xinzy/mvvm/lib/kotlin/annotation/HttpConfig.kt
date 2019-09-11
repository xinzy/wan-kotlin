package com.xinzy.mvvm.lib.kotlin.annotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention
annotation class HttpConfig(val timeout: Long = 5L, val unsafe: Boolean = false)