package com.nolleowass.emotionline.model

import java.util.*

data class Diary(
    val id: Int,
    val content: String,
    val createAt: Date,
    val accountId: Int,
    val emotionPoint: Int
)