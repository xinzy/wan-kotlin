package com.xinzy.mvvm.lib.kotlin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xinzy.mvvm.lib.kotlin.BR
import com.xinzy.mvvm.lib.kotlin.annotation.Layout
import com.xinzy.mvvm.lib.kotlin.annotation.ViewModelId
import com.xinzy.mvvm.lib.kotlin.view.widget.ProgressDialog
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VDB: ViewDataBinding, VM: BaseViewModel<*>> : AppCompatActivity() {

    protected lateinit var mDataBinding: VDB
    protected lateinit var mViewModel: VM

    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDataBinding = DataBindingUtil.setContentView(this, getLayoutInternal())
        mViewModel = createViewModelInternal()

        bindViewModelInternal()
        viewDataBinding(mDataBinding, mViewModel)
    }

    protected open fun onViewDataBinding(dataBinding: VDB, viewModel: VM) {}

    protected open fun getLayoutId() = -1

    protected open fun createViewModel(): VM? = null

    protected open fun getViewModelId() = BR.viewModel

    protected open fun onHandleSingleAction(action: Int) {}

    protected fun showProgress() = mProgressDialog?.show() ?: run { mProgressDialog = ProgressDialog(this).also { it.show() } }

    protected fun dismissProgress() = mProgressDialog?.dismiss()

    private fun viewDataBinding(dataBinding: VDB, viewModel: VM) {
        lifecycle.addObserver(viewModel)
        viewModel.actionEvent.observe(this, Observer { action -> doAction(action) })
        onViewDataBinding(dataBinding, viewModel)
    }

    private fun doAction(action: Int) {
        when (action) {
            BaseViewModel.ACTION_FINISH -> finish()
            BaseViewModel.ACTION_SHOW_PROGRESS -> showProgress()
            BaseViewModel.ACTION_DISMIS_PROGRESS -> dismissProgress()
            else -> onHandleSingleAction(action)
        }
    }

    private fun bindViewModelInternal() {
        val id = getViewModelIdInternal()
        if (id >= 0) {
            mDataBinding.setVariable(id, mViewModel)
        } else{
            try {
                val method = mDataBinding.javaClass.getDeclaredMethod("setViewModel", mViewModel.javaClass)
                method.invoke(mDataBinding, mViewModel)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getViewModelIdInternal(): Int {
        val id = getViewModelId()
        if (id >= 0) return id
        return javaClass.getAnnotation(ViewModelId::class.java)?.value ?: -1
    }

    private fun createViewModelInternal(): VM {
        val vm = createViewModel()
        if (vm != null) return vm

        val superClazz = javaClass.genericSuperclass
        val clazz = if (superClazz is ParameterizedType) (superClazz.actualTypeArguments[1] as? Class<VM> ?: BaseViewModel::class.java)
                    else BaseViewModel::class.java

        return ViewModelProviders.of(this).get(clazz) as VM
    }

    private fun getLayoutInternal(): Int {
        val id = getLayoutId()
        if (id != -1) return id

        return javaClass.getAnnotation(Layout::class.java)?.value ?: throw IllegalStateException("must override method getItemLayoutId or set annotation `Layout`");
    }
}