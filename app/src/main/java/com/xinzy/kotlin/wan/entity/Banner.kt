package com.xinzy.kotlin.wan.entity

import androidx.annotation.Keep

@Keep
data class Banner(
    var id: Int = 0,
    var title: String = "",
    var imagePath: String? = null,
    var url: String? = null,
    var desc: String? = null,
    var order: Int = 0,
    var isVisible: Int = 0,
    var type: Int = 0
)