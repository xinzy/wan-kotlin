package com.xinzy.kotlin.wan.biz.main.fragment

import androidx.lifecycle.Observer
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.biz.main.viewmodel.NavigationProjectViewModel
import com.xinzy.kotlin.wan.common.adapter.ChapterPagerAdapter
import com.xinzy.kotlin.wan.databinding.FragmentNavigationProjectBinding
import com.xinzy.mvvm.lib.kotlin.annotation.Layout
import com.xinzy.mvvm.lib.kotlin.base.BaseFragment

@Layout(R.layout.fragment_navigation_project)
class NavigationProjectFragment : BaseFragment<FragmentNavigationProjectBinding, NavigationProjectViewModel>() {

    override fun onViewDataBinding(dataBinding: FragmentNavigationProjectBinding, viewModel: NavigationProjectViewModel) {
        viewModel.projectChapters.observe(this, Observer { chapters ->
            val adapter = ChapterPagerAdapter(childFragmentManager, chapters) { ProjectFragment.newInstance(it) }
            dataBinding.viewPager.adapter = adapter
            dataBinding.tabs.setupWithViewPager(dataBinding.viewPager)
        })
    }
}