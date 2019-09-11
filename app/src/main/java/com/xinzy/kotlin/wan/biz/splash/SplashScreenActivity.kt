package com.xinzy.kotlin.wan.biz.splash

import android.os.Bundle
import android.os.Handler
import androidx.core.os.postDelayed
import com.alibaba.android.arouter.launcher.ARouter
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.databinding.ActivitySplashScreenBinding
import com.xinzy.kotlin.wan.util.ROUTER_MAIN
import com.xinzy.mvvm.lib.kotlin.annotation.Layout
import com.xinzy.mvvm.lib.kotlin.base.BaseActivity
import com.xinzy.mvvm.lib.kotlin.permission.runWithPermissions

@Layout(R.layout.activity_splash_screen)
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding, SplashScreenViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runWithPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE) {
            gotoMain()
        }
    }

    private fun gotoMain() {

        Handler().postDelayed(1000) {
            ARouter.getInstance().build(ROUTER_MAIN).navigation(this)
            finish()
        }

    }
}
