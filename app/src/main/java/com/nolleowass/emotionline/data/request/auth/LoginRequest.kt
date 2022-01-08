package com.nolleowass.emotionline.data.request.auth

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("password") val password: String
)