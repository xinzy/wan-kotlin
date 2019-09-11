package com.xinzy.kotlin.wan.biz.chapter.fragment

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.biz.chapter.viewmodel.TopicViewModel
import com.xinzy.kotlin.wan.databinding.FragmentTopicBinding
import com.xinzy.kotlin.wan.entity.Chapter
import com.xinzy.kotlin.wan.util.KEY_CHAPTER
import com.xinzy.mvvm.lib.kotlin.annotation.Layout
import com.xinzy.mvvm.lib.kotlin.base.BaseFragment

@Layout(R.layout.fragment_topic)
class TopicFragment : BaseFragment<FragmentTopicBinding, TopicViewModel>() {

    private lateinit var mChapter: Chapter

    companion object {
        fun newInstance(chapter: Chapter) = TopicFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_CHAPTER, chapter)
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mChapter = arguments!!.getParcelable(KEY_CHAPTER)!!
    }

    override fun createViewModel(): TopicViewModel? {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return modelClass.getConstructor(Application::class.java, Chapter::class.java)
                    .newInstance(context?.applicationContext, mChapter)
            }
        }).get(TopicViewModel::class.java)
    }
}