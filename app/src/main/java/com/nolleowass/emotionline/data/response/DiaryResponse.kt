package com.nolleowass.emotionline.data.response

import com.google.gson.annotations.SerializedName
import com.nolleowass.emotionline.extension.toDate
import com.nolleowass.emotionline.model.Diary
import java.sql.Date
import java.util.*

data class DiaryListResponse(
    @SerializedName("diary_list") val list: List<DiaryResponse>
)

data class DiaryResponse(
    @SerializedName("diary_id") val id: Int,
    @SerializedName("content") val content: String,
    @SerializedName("create_at") val createAt: String,
    @SerializedName("account_id") val accountId: Int,
    @SerializedName("emotion_point") val emotionPoint: Int
)

fun DiaryResponse.toModel() =
    Diary(id, content, createAt.toDate("yyyy-MM-dd"), accountId, emotionPoint)
