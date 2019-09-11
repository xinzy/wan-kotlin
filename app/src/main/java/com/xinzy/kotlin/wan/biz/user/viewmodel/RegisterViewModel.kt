package com.xinzy.kotlin.wan.biz.user.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.api.WanApiModel
import com.xinzy.kotlin.wan.entity.User
import com.xinzy.mvvm.lib.kotlin.base.BaseViewModel

class RegisterViewModel(application: Application) : BaseViewModel<WanApiModel>(application) {

    var username: String = ""
    var password: String = ""
    var confirm: String = ""

    val errorMessage = ObservableField<String>("")

    @SuppressLint("CheckResult")
    fun onRegister() {
        if (invalidate()) {
            showProgress()
            mModel!!.register(username, password, confirm).subscribe({
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
                errorMessage.set(getString(R.string.error_network))
            })
        }
    }

    fun onClearError(input: String) {
        errorMessage.set("")
    }

    private fun getString(resId: Int) = getApplication<Application>().getString(resId)

    private fun invalidate(): Boolean {
        return when {
            username.isEmpty() -> {
                errorMessage.set(getString(R.string.error_username_empty))
                false
            }
            password.isEmpty() -> {
                errorMessage.set(getString(R.string.error_password_empty))
                false
            }
            confirm.isEmpty() -> {
                errorMessage.set(getString(R.string.error_confirm_empty))
                false
            }
            password != confirm ->{
                errorMessage.set(getString(R.string.error_password_diff))
                false
            }
            password.length !in 6..18 ->{
                errorMessage.set(getString(R.string.error_password_length))
                false
            }
            else -> true
        }
    }
}