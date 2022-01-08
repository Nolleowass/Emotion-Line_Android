package com.nolleowass.emotionline.model

data class User(
    val accountId: Int,
    val userId: String,
    val name: String,
    val isPublic: Boolean,
    val imageUrl: String
)
