package com.xinzy.kotlin.wan.biz.user.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.api.WanApiModel
import com.xinzy.kotlin.wan.entity.User
import com.xinzy.mvvm.lib.kotlin.base.BaseViewModel

class LoginViewModel(application: Application) : BaseViewModel<WanApiModel>(application) {

    var username: String = ""
    var password: String = ""

    val errorMessage = ObservableField<String>("")

    @SuppressLint("CheckResult")
    fun onLogin() {
        if (validate()) {
            showProgress()
            mModel!!.login(username, password).subscribe({
                dismissProgress()
                if (it.isSuccess()) {
                    val user = it.getData()

                    User.me().copy(user).save(getApplication())
                    finish()
                } else {
                    errorMessage.set(it.message)
                }
            }, {
                dismissProgress()
            })
        }
    }

    private fun validate() = when {
        username.isEmpty() -> {
            errorMessage.set(getString(R.string.error_username_empty))
            false
        }
        password.isEmpty() -> {
            errorMessage.set(getString(R.string.error_confirm_empty))
            false
        }

        else -> true
    }

    private fun getString(resId: Int) = getApplication<Application>().getString(resId)

    fun onClearError(input: String) {
        errorMessage.set("")
    }
}