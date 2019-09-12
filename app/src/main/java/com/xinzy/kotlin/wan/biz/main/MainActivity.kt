package com.xinzy.kotlin.wan.biz.main

import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.postDelayed
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.biz.main.fragment.*
import com.xinzy.kotlin.wan.biz.main.viewmodel.MainViewModel
import com.xinzy.kotlin.wan.databinding.ActivityMainBinding
import com.xinzy.kotlin.wan.databinding.NavHeaderMainBinding
import com.xinzy.kotlin.wan.entity.User
import com.xinzy.kotlin.wan.util.ROUTER_LOGIN
import com.xinzy.kotlin.wan.util.ROUTER_MAIN
import com.xinzy.mvvm.lib.kotlin.annotation.Layout
import com.xinzy.mvvm.lib.kotlin.base.BaseActivity

@Route(path = ROUTER_MAIN)
@Layout(R.layout.activity_main)
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val mHomeFragment = NavigationHomeFragment()
    private val mChapterFragment = NavigationChapterFragment()
    private val mWeixinFragment = NavigationWeixinFragment()
    private val mProjectFragment = NavigationProjectFragment()
    private val mNavFragment = NavigationNavFragment()

    private var mSelectedFragment: Fragment? = null

    private lateinit var mNavHeaderBinding: NavHeaderMainBinding

    override fun onViewDataBinding(dataBinding: ActivityMainBinding, viewModel: MainViewModel) {
        dataBinding.navView.setOnNavigationItemSelectedListener(this)
        dataBinding.navView.selectedItemId = R.id.navigation_home

        mNavHeaderBinding = NavHeaderMainBinding.inflate(LayoutInflater.from(this), dataBinding.leftNavView, false)
        mNavHeaderBinding.viewModel = viewModel
        dataBinding.leftNavView.addHeaderView(mNavHeaderBinding.root)
    }

    override fun onResume() {
        super.onResume()
        mNavHeaderBinding.executePendingBindings()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home    -> selectFragment(mHomeFragment)
            R.id.navigation_chapter -> selectFragment(mChapterFragment)
            R.id.navigation_weixin  -> selectFragment(mWeixinFragment)
            R.id.navigation_project -> selectFragment(mProjectFragment)
            R.id.navigation_nav     -> selectFragment(mNavFragment)
        }

        return true
    }

    fun onCheckUser(view: View) {
        if (User.me().isLogin()) return

        ARouter.getInstance().build(ROUTER_LOGIN).navigation(this)
        Handler().postDelayed(500) { mDataBinding.drawerLayout.closeDrawer(GravityCompat.START) }
    }

    private fun selectFragment(fragment: Fragment) {
        if (mSelectedFragment == fragment) return

        val tag = fragment.javaClass.simpleName
        if (mSelectedFragment == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment, tag).commit()
        } else {
            if (supportFragmentManager.findFragmentByTag(tag) != null) {
                supportFragmentManager.beginTransaction().show(fragment).hide(mSelectedFragment!!).commit()
            } else {
                supportFragmentManager.beginTransaction().hide(mSelectedFragment!!)
                    .add(R.id.fragmentContainer, fragment, tag).commit()
            }
        }
        mSelectedFragment = fragment
    }
}
