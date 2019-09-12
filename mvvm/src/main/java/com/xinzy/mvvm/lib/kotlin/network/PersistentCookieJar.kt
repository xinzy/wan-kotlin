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
        val dir = getCookieDir(url) ?: return
        cookies.forEach {
            if (it.persistent()) {
                val name = it.name()
                val file = File(dir, name)
                if (isExpires(it)) {
                    if (file.exists()) file.delete()
                } else {
                    val content = "${it.value()}|${it.domain()}|${it.path()}|${it.expiresAt()}|${it.hostOnly()}|${it.httpOnly()}"
                    try {
                        file.writeText(content)
                    } catch (e: Throwable) {}
                }
            }
        }
    }

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        val list = mutableListOf<Cookie>()
        val dir = getCookieDir(url) ?: return list

        dir.listFiles()?.forEach { file ->
            file.readLines().filter { it.isNotEmpty() }.map { it.split("|") }.filter { it.size == 6 }.first().let {
                val value = it[0]
                val domain = it[1]
                val path = it[2]
                val expiresAt = it[3].toLong()
                val hostOnly = it[4].toBoolean()
                val httpOnly = it[5].toBoolean()
                if (expiresAt > System.currentTimeMillis()) {
                    val builder = Cookie.Builder().name(file.name).value(value).domain(domain).path(path).expiresAt(expiresAt)
                    if (hostOnly) builder.hostOnlyDomain(domain)
                    if (httpOnly) builder.httpOnly()

                    val cookie = builder.build()
                    if (cookie.matches(url)) {
                        list.add(cookie)
                    }
                }
            }
        }
        return list
    }

    fun isExpires(cookie: Cookie) = System.currentTimeMillis() > cookie.expiresAt()

    private fun getCookieDir(url: HttpUrl): File? {
        val filename = url.host().md5()
        return if (mCookieDir != null) File(mCookieDir, filename).apply { if (!exists()) mkdirs() } else null
    }
}
