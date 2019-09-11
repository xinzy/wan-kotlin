package com.xinzy.kotlin.wan.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApiResult<T>(
    @SerializedName("errorCode")
    val code: Int = 0,
    @SerializedName("errorMsg")
    val message: String? = null,
    private val data: T? = null

) {
    fun isSuccess() = code == 0

    fun getData(): T = data!!
}