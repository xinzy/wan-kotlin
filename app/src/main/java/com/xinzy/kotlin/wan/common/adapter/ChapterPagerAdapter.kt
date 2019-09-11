package com.xinzy.kotlin.wan.common.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xinzy.kotlin.wan.entity.Chapter

class ChapterPagerAdapter(fm: FragmentManager, private val chapters: List<Chapter>?, private val creator: (Chapter) -> Fragment)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return creator(chapters!![position])
    }

    override fun getCount() = chapters?.size ?: 0

    override fun getPageTitle(position: Int): CharSequence? {
        return chapters!![position].getName()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) { }
}