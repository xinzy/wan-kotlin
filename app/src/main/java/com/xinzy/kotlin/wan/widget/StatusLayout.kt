package com.xinzy.kotlin.wan.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ViewFlipper
import androidx.annotation.Keep
import androidx.databinding.BindingAdapter
import com.xinzy.mvvm.lib.kotlin.view.binding.BindingAction

class StatusLayout : ViewFlipper {
    private var mEmptyView: EmptyView = EmptyView(context)
    private var mErrorView: ErrorView = ErrorView(context)

    private var mOnRetryListener: OnRetryListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    fun setOnRetryListener(l: OnRetryListener?) {
        mOnRetryListener = l
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        check(childCount <= 1) { "StatusLayout must have only on child view" }

        addView(mEmptyView)
        addView(mErrorView)
        mErrorView.setOnClickListener { mOnRetryListener?.onRetry(this) }
    }

    fun show(status: Status) {
        displayedChild = status.index
    }
}

@BindingAdapter("onRetryAction")
fun setRetryListener(layout: StatusLayout, action: BindingAction) {
    layout.setOnRetryListener(object : OnRetryListener {
        override fun onRetry(layout: StatusLayout) {
            action.call()
        }
    })
}

@BindingAdapter("status")
fun setStatus(layout: StatusLayout, status: Status) {
    layout.show(status)
}

interface OnRetryListener {
    fun onRetry(layout: StatusLayout)
}

@Keep
enum class Status(val index: Int) {
    Normal(0),
    Empty(1),
    Error(2);
}