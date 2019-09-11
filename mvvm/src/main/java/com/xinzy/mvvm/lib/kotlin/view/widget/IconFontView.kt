package com.xinzy.mvvm.lib.kotlin.view.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import com.xinzy.mvvm.lib.kotlin.R

class IconFontView : TextView {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        gravity = Gravity.CENTER
        val ta = context!!.obtainStyledAttributes(attrs, R.styleable.IconFontView, defStyleAttr, 0)
        val path = ta.getString(R.styleable.IconFontView_fontPath) ?: "fonts/iconfont.ttf"
        typeface = Typeface.createFromAsset(context.assets, path)
        ta.recycle()
    }
}