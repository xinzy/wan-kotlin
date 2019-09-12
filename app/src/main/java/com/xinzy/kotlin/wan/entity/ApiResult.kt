package com.xinzy.kotlin.wan.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.lang.IllegalStateException

@Keep
data class ApiResult<T>(
    @SerializedName("errorCode")
    var code: Int = 0,
    @SerializedName("errorMsg")
    var message: String? = null,
    private var data: T? = null

) {
    companion object {
        const val USER_LOGOUT = -1001
    }

    fun isLogout() = code == USER_LOGOUT

    fun isSuccess() = code == 0

    fun getData(): T = data!!
}