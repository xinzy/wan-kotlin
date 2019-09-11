package com.xinzy.mvvm.lib.kotlin.network

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

class TrustHostnameVerifier() : HostnameVerifier {

    override fun verify(hostname: String?, session: SSLSession?): Boolean = true
}