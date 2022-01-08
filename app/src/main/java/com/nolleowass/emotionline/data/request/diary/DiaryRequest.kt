package com.nolleowass.emotionline.data.request.diary

import com.google.gson.annotations.SerializedName

data class DiaryRequest(
    @SerializedName("content") val content: String
)