package com.xinzy.kotlin.wan.biz.main.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import com.alibaba.android.arouter.launcher.ARouter
import com.xinzy.kotlin.wan.api.WanApiModel
import com.xinzy.kotlin.wan.entity.Coin
import com.xinzy.kotlin.wan.entity.User
import com.xinzy.kotlin.wan.util.ROUTER_LOGIN
import com.xinzy.mvvm.lib.kotlin.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel<WanApiModel>(application) {

    val userCoin = ObservableField<Coin>()

    @SuppressLint("CheckResult")
    private fun getCoin() {
        mModel!!.coin().subscribe {
            if (it.isSuccess()) {
                userCoin.set(it.getData())
            } else if (it.isLogout()) {
                User.me().logout(getApplication())
                ARouter.getInstance().build(ROUTER_LOGIN).navigation()
            }
        }
    }

    @SuppressLint("CheckResult")
    fun onLogout() {
        showProgress()
        mModel!!.logout().subscribe {
            dismissProgress()
            if (it.isSuccess()) {
                User.me().logout(getApplication())
            }
        }
    }

    override fun onResume() {
        if (!User.me().isLogin()) return
        getCoin()
    }
}