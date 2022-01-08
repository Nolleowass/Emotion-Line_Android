package com.nolleowass.emotionline.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(pattern: String): Date? {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.parse(this)
}
