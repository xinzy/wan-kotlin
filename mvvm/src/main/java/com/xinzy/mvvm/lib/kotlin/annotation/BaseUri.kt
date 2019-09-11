package com.xinzy.mvvm.lib.kotlin.annotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUri(val value: String)