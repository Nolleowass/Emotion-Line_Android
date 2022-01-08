package com.nolleowass.emotionline.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user_id") val id: String,
    @SerializedName("token") val token: String
)
