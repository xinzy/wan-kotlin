package com.xinzy.kotlin.wan.biz.main.fragment

import androidx.lifecycle.Observer
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.biz.main.viewmodel.NavigationWeixinViewModel
import com.xinzy.kotlin.wan.common.adapter.ChapterPagerAdapter
import com.xinzy.kotlin.wan.databinding.FragmentNavigationWeixinBinding
import com.xinzy.mvvm.lib.kotlin.annotation.Layout
import com.xinzy.mvvm.lib.kotlin.base.BaseFragment

@Layout(R.layout.fragment_navigation_weixin)
class NavigationWeixinFragment : BaseFragment<FragmentNavigationWeixinBinding, NavigationWeixinViewModel>() {

    override fun onViewDataBinding(dataBinding: FragmentNavigationWeixinBinding, viewModel: NavigationWeixinViewModel) {
        viewModel.weixinListEvent.observe(this, Observer { list ->
            val adapter = ChapterPagerAdapter(childFragmentManager, list) { chapter -> WeixinFragment.newInstance(chapter) }
            dataBinding.viewPager.adapter = adapter
            dataBinding.tabs.setupWithViewPager(dataBinding.viewPager)
        })
    }
}