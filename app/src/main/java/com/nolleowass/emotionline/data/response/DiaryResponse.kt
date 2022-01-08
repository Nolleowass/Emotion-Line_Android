package com.nolleowass.emotionline.data.response

import com.google.gson.annotations.SerializedName
import com.nolleowass.emotionline.model.Diary
import java.util.*

data class DiaryResponse(
    @SerializedName("diary_id") val id: Int,
    @SerializedName("content") val content: String,
    @SerializedName("createAt") val createAt: Date,
    @SerializedName("account_id") val accountId: Int,
    @SerializedName("emotion_point") val emotionPoint: Int
)

fun DiaryResponse.toModel() =
    Diary(id, content, createAt, accountId, emotionPoint)
