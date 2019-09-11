package com.xinzy.kotlin.wan.biz.main.fragment

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.biz.main.viewmodel.WeixinViewModel
import com.xinzy.kotlin.wan.databinding.FragmentWeixinBinding
import com.xinzy.kotlin.wan.entity.Chapter
import com.xinzy.kotlin.wan.util.KEY_CHAPTER
import com.xinzy.mvvm.lib.kotlin.annotation.Layout
import com.xinzy.mvvm.lib.kotlin.base.BaseFragment

@Layout(R.layout.fragment_weixin)
class WeixinFragment : BaseFragment<FragmentWeixinBinding, WeixinViewModel>() {

    private lateinit var mChapter: Chapter

    companion object {
        fun newInstance(chapter: Chapter) = WeixinFragment().apply {
                val args = Bundle().apply {
                    putParcelable(KEY_CHAPTER, chapter)
                }
                this.arguments = args
            }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mChapter = arguments!!.getParcelable(KEY_CHAPTER)!!
    }

    override fun createViewModel(): WeixinViewModel? {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return modelClass.getDeclaredConstructor(Application::class.java, Chapter::class.java)
                    .newInstance(context?.applicationContext, mChapter)
            }
        }) .get(WeixinViewModel::class.java)
    }
}