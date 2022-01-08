package com.nolleowass.emotionline.data.request.auth

import com.google.gson.annotations.SerializedName

class UserPublicRequest(
    @SerializedName("is_public") val isPublic: Boolean
)
