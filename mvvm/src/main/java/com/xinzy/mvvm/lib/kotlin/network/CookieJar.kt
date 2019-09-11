package com.xinzy.mvvm.lib.kotlin.network

import android.content.Context
import com.xinzy.mvvm.lib.kotlin.common.Commons
import com.xinzy.mvvm.lib.kotlin.util.md5
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.io.File

class PersistentCookieJar : CookieJar {
    private val mCookieDir: File? = Commons.applicationContext?.getDir("cookie", Context.MODE_PRIVATE)?.apply { if (!exists()) mkdirs() }

    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        val sb = StringBuffer()
        cookies.forEach {
            if (it.persistent()) {
                sb.append(it.name()).append(':')
                val content = "${it.value()}|${it.domain()}|${it.path()}|${it.expiresAt()}|${it.hostOnly()}|${it.httpOnly()}"
                sb.append(content).append('\n')
            }
        }
        if (sb.isNotEmpty()) {
            try {
                getCookieFile(url)?.writeText(sb.toString())
            } catch (e: Exception) {}
        }
    }

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        val list = mutableListOf<Cookie>()
        try {
            getCookieFile(url)?.let {
                it.readLines().filter { item -> item.isNotEmpty() }.map { item -> item.split(":") }.filter { item -> item.size == 2 }
                    .forEach { item ->
                        val content = item[1].split("|")
                        if (content.size == 6) {
                            val value = content[0]
                            val domain = content[1]
                            val path = content[2]
                            val expiresAt = content[3].toLong()
                            val hostOnly = content[4].toBoolean()
                            val httpOnly = content[5].toBoolean()
                            if (expiresAt > System.currentTimeMillis()) {
                                val builder = Cookie.Builder().name(item[0]).value(value).domain(domain).path(path).expiresAt(expiresAt)
                                if (hostOnly) builder.hostOnlyDomain(domain)
                                if (httpOnly) builder.httpOnly()

                                val cookie = builder.build()
                                if (cookie.matches(url)) {
                                    list.add(cookie)
                                }
                            }
                        }
                    }
            }
        } catch (e: Exception) { }
        return list
    }

    private fun getCookieFile(url: HttpUrl): File? {
        val filename = url.host().md5()
        return if (mCookieDir != null) File(mCookieDir, filename) else null
    }
}
