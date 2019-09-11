package com.xinzy.kotlin.wan.biz.main.fragment

import androidx.lifecycle.Observer
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.biz.main.viewmodel.NavigationNavViewModel
import com.xinzy.kotlin.wan.databinding.FragmentNavigationNavBinding
import com.xinzy.mvvm.lib.kotlin.annotation.Layout
import com.xinzy.mvvm.lib.kotlin.base.BaseFragment

@Layout(R.layout.fragment_navigation_nav)
class NavigationNavFragment : BaseFragment<FragmentNavigationNavBinding, NavigationNavViewModel>() {

    override fun onViewDataBinding(dataBinding: FragmentNavigationNavBinding, viewModel: NavigationNavViewModel) {
        viewModel.itemNavigationPosition.observe(this, Observer { index ->
            dataBinding.navigationRecyclerView.smoothScrollToPosition(index)
        })
        viewModel.itemNavigationScrollPosition.observe(this, Observer { position ->
            dataBinding.catRecyclerView.smoothScrollToPosition(position)
        })
        dataBinding.navigationRecyclerView.addOnScrollListener(viewModel.onNavigationScrollListener)
    }
}