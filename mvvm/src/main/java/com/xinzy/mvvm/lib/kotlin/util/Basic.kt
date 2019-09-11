package com.xinzy.mvvm.lib.kotlin.util

import android.content.Context
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.nio.charset.Charset
import java.security.MessageDigest


fun View.setPadding(h: Int, v: Int) = setPadding(h, v, h, v)

fun Context.toast(msgId: Int) = toast(getString(msgId))
fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

fun Context.dp2px(dp: Int) = dp2px(dp.toFloat())
fun Context.dp2px(dp: Float) = (resources.displayMetrics.density * dp + .5f).toInt()

fun View.dp2px(dp: Int) = dp2px(dp.toFloat())
fun View.dp2px(dp: Float) = (resources.displayMetrics.density * dp + .5f).toInt()

fun TextView.doBeforeTextChanged(action: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit) = addTextChangedListener(beforeTextChanged = action)
fun TextView.doAfterTextChanged(action: (s: Editable?) -> Unit) = addTextChangedListener(afterTextChanged = action)
fun TextView.doOnTextChanged(action: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit) = addTextChangedListener(onTextChanged = action)

fun TextView.addTextChangedListener(
    beforeTextChanged: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ ->  },
    afterTextChanged: (s: Editable?) -> Unit = {  },
    onTextChanged: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { _, _, _,_ ->  }
): TextWatcher {
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = afterTextChanged(s)
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = beforeTextChanged(s, start, count, after)
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = onTextChanged(s, start, before, count)
    }
    addTextChangedListener(watcher)

    return watcher
}

fun RecyclerView.edge(
    endAction: (view: RecyclerView) -> Unit = {},
    startAction: (view: RecyclerView) -> Unit = {}
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState != RecyclerView.SCROLL_STATE_IDLE) return

            val layoutManager = recyclerView.layoutManager
            if (layoutManager !is LinearLayoutManager) return

            if (layoutManager.orientation == RecyclerView.VERTICAL) {
                if (!recyclerView.canScrollVertically(1)) {
                    endAction(recyclerView)
                } else if (!recyclerView.canScrollVertically(-1)) {
                    startAction(recyclerView)
                }
            } else {
                if (!recyclerView.canScrollHorizontally(1)) {
                    endAction(recyclerView)
                } else if (!recyclerView.canScrollHorizontally(-1)) {
                    startAction(recyclerView)
                }
            }
        }
    })
}

fun String.toUri() = Uri.parse(this)
fun File.toUri() = Uri.fromFile(this)
fun Uri.toFile(): File {
    require(scheme == "file") { "Uri lacks 'file' scheme: $this" }
    return File(path)
}

fun String.sha1() = hash("SHA-1")
fun String.sha256() = hash("SHA-256")
fun String.sha512() = hash("SHA-512")
fun String.md5() = hash("MD5")
fun String.base64Encode(charset: Charset = Charsets.UTF_8) = String(Base64.encode(toByteArray(), Base64.DEFAULT), charset)
fun String.base64Decode(charset: Charset = Charsets.UTF_8) = String(Base64.decode(toByteArray(), Base64.DEFAULT), charset)

internal fun String.hash(type: String) = try {
    MessageDigest.getInstance(type).digest(toByteArray()).joinToString(separator = "") { String.format("%02x", it) }
} catch (e: Exception) {
    ""
}