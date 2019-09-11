package com.xinzy.kotlin.wan.biz.user

import android.view.MenuItem
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.biz.user.viewmodel.RegisterViewModel
import com.xinzy.kotlin.wan.databinding.ActivityRegisterBinding
import com.xinzy.kotlin.wan.util.ROUTER_LOGIN
import com.xinzy.kotlin.wan.util.ROUTER_REGISTER
import com.xinzy.mvvm.lib.kotlin.annotation.Layout
import com.xinzy.mvvm.lib.kotlin.base.BaseActivity

@Route(path = ROUTER_REGISTER)
@Layout(R.layout.activity_register)
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {

    override fun onViewDataBinding(dataBinding: ActivityRegisterBinding, viewModel: RegisterViewModel) {
        setSupportActionBar(dataBinding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    fun onLogin(view: View) {
        ARouter.getInstance().build(ROUTER_LOGIN).navigation(this)
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
