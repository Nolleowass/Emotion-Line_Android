package com.nolleowass.emotionline.data.api

import com.nolleowass.emotionline.data.response.UserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ProfileAPI {

    @GET("profile/list")
    fun list(): Single<List<UserResponse>>
}
