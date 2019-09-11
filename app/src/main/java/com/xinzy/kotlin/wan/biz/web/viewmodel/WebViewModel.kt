package com.xinzy.kotlin.wan.biz.web.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.xinzy.mvvm.lib.kotlin.base.BaseModel
import com.xinzy.mvvm.lib.kotlin.base.BaseViewModel
import com.xinzy.mvvm.lib.kotlin.util.logW

class WebViewModel(application: Application) : BaseViewModel<BaseModel>(application) {

    var progressVisible = ObservableBoolean(true)
    var progress = ObservableInt()

    var receiverTitleAction: MutableLiveData<String> = MutableLiveData()

    private val mWebViewClient = ViewClient()
    private val mWebChromeClient = ChromeClient()

    fun getWebViewClient(): WebViewClient {
        return mWebViewClient
    }

    fun getWebChromeClient(): WebChromeClient {
        return mWebChromeClient
    }

    internal inner class ChromeClient : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            receiverTitleAction.postValue(title)
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            progress.set(newProgress)
        }
    }

    internal inner class ViewClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            progressVisible.set(true)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            progressVisible.set(false)
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            logW("receiver ssl error")
            handler?.proceed()
        }
    }
}