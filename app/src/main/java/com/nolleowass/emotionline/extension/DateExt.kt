package com.nolleowass.emotionline.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.toString(pattern: String): String {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(this)
}