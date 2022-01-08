package com.nolleowass.emotionline.data.request.auth

import com.google.gson.annotations.SerializedName

data class UserNameRequest(
    @SerializedName("user_name") val name: String
)
