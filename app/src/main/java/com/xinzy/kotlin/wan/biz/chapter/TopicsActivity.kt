package com.xinzy.kotlin.wan.biz.chapter

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.biz.chapter.fragment.TopicFragment
import com.xinzy.kotlin.wan.common.adapter.ChapterPagerAdapter
import com.xinzy.kotlin.wan.databinding.ActivityTopicsBinding
import com.xinzy.kotlin.wan.entity.Chapter
import com.xinzy.kotlin.wan.util.KEY_CHAPTER
import com.xinzy.kotlin.wan.util.ROUTER_TOPICS
import com.xinzy.mvvm.lib.kotlin.annotation.Layout
import com.xinzy.mvvm.lib.kotlin.base.BaseActivity
import com.xinzy.mvvm.lib.kotlin.base.BaseViewModel

@Layout(R.layout.activity_topics)
@Route(path = ROUTER_TOPICS)
class TopicsActivity : BaseActivity<ActivityTopicsBinding, BaseViewModel<*>>() {

    @Autowired(name = KEY_CHAPTER, required = true)
    @JvmField
    var mChapter: Chapter? = null

    private lateinit var mActionBar: ActionBar

    override fun onViewDataBinding(dataBinding: ActivityTopicsBinding, viewModel: BaseViewModel<*>) {
        ARouter.getInstance().inject(this)

        setSupportActionBar(dataBinding.toolBar)
        mActionBar = supportActionBar!!
        mActionBar.title = mChapter?.getName()
        mActionBar.setDisplayHomeAsUpEnabled(true)

        val adapter = ChapterPagerAdapter(supportFragmentManager, mChapter?.children) { chapter -> TopicFragment.newInstance(chapter) }
        dataBinding.viewPager.adapter = adapter
        dataBinding.tabs.setupWithViewPager(dataBinding.viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
