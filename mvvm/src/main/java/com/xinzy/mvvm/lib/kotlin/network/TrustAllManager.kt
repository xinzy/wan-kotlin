package com.xinzy.mvvm.lib.kotlin.network

import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

class TrustAllManager() : X509TrustManager {
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

    override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
}