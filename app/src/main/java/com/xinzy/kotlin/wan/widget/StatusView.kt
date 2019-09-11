package com.xinzy.kotlin.wan.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import com.xinzy.kotlin.wan.R
import com.xinzy.mvvm.lib.kotlin.view.widget.IconFontView

class EmptyView: StatusView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getIconText() = R.string.icon_empty

    override fun getInfoText() = R.string.no_data
}

class ErrorView: StatusView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getIconText() = R.string.icon_fail

    override fun getInfoText() = R.string.retry
}

abstract class StatusView : LinearLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL

        addSpace(3f)

        val icon = IconFontView(context)
        icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80f)
        icon.setTextColor(0xFF9DBEDA.toInt())
        icon.setText(getIconText())
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        addView(icon, lp)

        val infoText = TextView(context)
        infoText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        infoText.setSingleLine()
        infoText.setTextColor(0xFFA8B8CC.toInt())
        infoText.setText(getInfoText())
        val lp2 =  LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        lp2.topMargin = (resources.displayMetrics.density * 20).toInt()
        addView(infoText, lp2)

        addSpace(5f)
    }

    protected abstract fun getIconText(): Int
    protected abstract fun getInfoText(): Int

    private fun addSpace(weight: Float) {
        val space = Space(context)

        val lp = LayoutParams(LayoutParams.MATCH_PARENT, 0)
        lp.weight = weight
        addView(space, lp)
    }
}