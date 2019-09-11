package com.xinzy.kotlin.wan

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.xinzy.kotlin.wan.entity.User
import com.xinzy.mvvm.lib.kotlin.network.TrustAllManager
import com.xinzy.mvvm.lib.kotlin.network.TrustHostnameVerifier
import com.xinzy.mvvm.lib.kotlin.network.createSSLSocketFactory
import okhttp3.OkHttpClient
import java.io.InputStream

class WanApplication : Application() {

    companion object {
        var sInstance: WanApplication? = null

        fun getInstance() = sInstance!!
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this

        initArouter()
        initGlide()
        User.me().load(this)
    }

    private fun initGlide() {
        val httpClient = OkHttpClient.Builder().hostnameVerifier(TrustHostnameVerifier())
            .sslSocketFactory(createSSLSocketFactory(), TrustAllManager()).build()
        Glide.get(this).registry.replace(GlideUrl::class.java, InputStream::class.java,
            OkHttpUrlLoader.Factory(httpClient))
    }

    private fun initArouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}