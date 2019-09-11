package com.xinzy.kotlin.wan.biz.web

import android.view.MenuItem
import android.webkit.WebView
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xinzy.kotlin.wan.R
import com.xinzy.kotlin.wan.biz.web.viewmodel.WebViewModel
import com.xinzy.kotlin.wan.databinding.ActivityWebViewBinding
import com.xinzy.kotlin.wan.util.KEY_TITLE
import com.xinzy.kotlin.wan.util.KEY_URL
import com.xinzy.kotlin.wan.util.ROUTER_WEB
import com.xinzy.mvvm.lib.kotlin.annotation.Layout
import com.xinzy.mvvm.lib.kotlin.base.BaseActivity

@Route(path = ROUTER_WEB)
@Layout(R.layout.activity_web_view)
class WebViewActivity : BaseActivity<ActivityWebViewBinding, WebViewModel>() {

    @Autowired(name = KEY_URL)
    @JvmField
    var mUrl: String? = null
    @Autowired(name = KEY_TITLE)
    @JvmField
    var mTitle: String? = null

    private lateinit var mWebView: WebView
    private lateinit var mActionBar: ActionBar

    override fun onViewDataBinding(dataBinding: ActivityWebViewBinding, viewModel: WebViewModel) {
        ARouter.getInstance().inject(this)

        setSupportActionBar(dataBinding.toolBar)
        mActionBar = supportActionBar!!
        mActionBar.setTitle(mTitle)
        mActionBar.setDisplayHomeAsUpEnabled(true)

        mWebView = dataBinding.webView
        mWebView.webChromeClient = viewModel.getWebChromeClient()
        mWebView.webViewClient = viewModel.getWebViewClient()

        val settings = mWebView.settings
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.setSupportZoom(true)

        mWebView.loadUrl(mUrl)

        initAction()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initAction() {
        mViewModel.receiverTitleAction.observe(this, Observer<String> { mActionBar.title = it })
    }

    override fun onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
