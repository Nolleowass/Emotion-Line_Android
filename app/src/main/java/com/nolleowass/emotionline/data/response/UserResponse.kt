package com.nolleowass.emotionline.data.response

import com.google.gson.annotations.SerializedName
import com.nolleowass.emotionline.model.User

data class ProfileListResponse(
    @SerializedName("profile_list") val list: List<UserResponse>
)

data class UserResponse(
    @SerializedName("account_id") val accountId: Int,
    @SerializedName("user_id") val userId: String,
    @SerializedName("user_name") val name: String,
    @SerializedName("is_public") val isPublic: Boolean,
    @SerializedName("profile_image_url") val imageUrl: String
)

fun UserResponse.toModel() =
    User(accountId, userId, name, isPublic, imageUrl)
