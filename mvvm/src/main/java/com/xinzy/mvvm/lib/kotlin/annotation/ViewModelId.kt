package com.xinzy.mvvm.lib.kotlin.annotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention
annotation class ViewModelId(val value: Int = -1)