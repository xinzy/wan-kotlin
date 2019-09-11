package com.xinzy.mvvm.lib.kotlin.util


import android.util.Log

private var isDebug = true

fun setDebugable(b: Boolean) {
    isDebug = b
}

fun logI(content: String) {
    if (!isDebug) return
    Log.i(generateTag(), content)
}

fun logV(content: String) {
    if (!isDebug) return
    Log.v(generateTag(), content)
}

fun logD(content: String) {
    if (!isDebug) return
    Log.d(generateTag(), content)
}

fun logW(content: String, tr: Throwable? = null) {
    if (!isDebug) return
    val tag = generateTag()
    if (tr == null) {
        Log.w(tag, content)
    } else {
        Log.w(tag, content, tr)
    }
}

fun logE(content: String, tr: Throwable? = null) {
    if (!isDebug) return
    val tag = generateTag()
    if (tr == null) {
        Log.e(tag, content)
    } else {
        Log.e(tag, content, tr)
    }
}

private fun generateTag(): String {
    val caller = Thread.currentThread().stackTrace[4]
    var callerClazzName = caller.className
    callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1)
    return "$callerClazzName.${caller.methodName}(L:${caller.lineNumber})"
}