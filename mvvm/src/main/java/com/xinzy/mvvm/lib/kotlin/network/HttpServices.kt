package com.xinzy.mvvm.lib.kotlin.network

import androidx.collection.ArrayMap
import com.xinzy.mvvm.lib.kotlin.annotation.BaseUri
import com.xinzy.mvvm.lib.kotlin.annotation.HttpConfig
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalStateException
import java.lang.RuntimeException
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager

import kotlin.reflect.KClass

private val mServices = ArrayMap<KClass<*>, Any>()

fun <I> getRetrofitApi(clazz: KClass<*>): I {
    if (mServices.contains(clazz)) {
        return mServices[clazz] as I
    }

    val uri = clazz.java.getAnnotation(BaseUri::class.java) ?: throw IllegalStateException("retrofit interface must as BaseUri annotation")
    val config = clazz.java.getAnnotation(HttpConfig::class.java)

    val builder = OkHttpClient.Builder().cookieJar(PersistentCookieJar())
    config?.let {
        if (it.unsafe) {
            builder.sslSocketFactory(createSSLSocketFactory(), TrustAllManager())
                .hostnameVerifier(TrustHostnameVerifier())
        }
        builder.readTimeout(it.timeout, TimeUnit.SECONDS).writeTimeout(it.timeout, TimeUnit.SECONDS)
            .connectTimeout(it.timeout, TimeUnit.SECONDS)
    }


    val retrofit = Retrofit.Builder().baseUrl(uri.value)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(builder.build())
        .build()

    val service = retrofit.create(clazz.java)
    mServices[clazz] = service

    return service as I
}

fun createSSLSocketFactory(): SSLSocketFactory {
    return try {
        val sc = SSLContext.getInstance("TLS")
        sc.init(null, arrayOf<TrustManager>(TrustAllManager()), SecureRandom())
        sc.socketFactory
    } catch (e: Exception) {
        throw RuntimeException("create ssl socket factory fail")
    }
}