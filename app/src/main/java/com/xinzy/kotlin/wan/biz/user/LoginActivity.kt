package com.xinzy.kotlin.wan.biz.user

import android.view.MenuItem
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.biz.user.viewmodel.LoginViewModel
import com.xinzy.kotlin.wan.databinding.ActivityLoginBinding
import com.xinzy.kotlin.wan.util.ROUTER_LOGIN
import com.xinzy.kotlin.wan.util.ROUTER_REGISTER
import com.xinzy.mvvm.lib.kotlin.annotation.Layout
import com.xinzy.mvvm.lib.kotlin.base.BaseActivity

@Layout(R.layout.activity_login)
@Route(path = ROUTER_LOGIN)
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun onViewDataBinding(dataBinding: ActivityLoginBinding, viewModel: LoginViewModel) {
        setSupportActionBar(dataBinding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    fun onRegister(view: View) {
        ARouter.getInstance().build(ROUTER_REGISTER).navigation(this)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
