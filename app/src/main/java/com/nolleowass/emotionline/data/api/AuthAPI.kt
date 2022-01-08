package com.nolleowass.emotionline.data.api

import com.nolleowass.emotionline.data.request.auth.LoginRequest
import com.nolleowass.emotionline.data.request.auth.UserNameRequest
import com.nolleowass.emotionline.data.request.auth.UserPublicRequest
import com.nolleowass.emotionline.data.response.LoginResponse
import com.nolleowass.emotionline.data.response.UserResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthAPI {

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Single<LoginResponse>

    @PUT("auth/name")
    fun updateName(@Body request: UserNameRequest): Completable

    @PUT("auth/is_public")
    fun updatePublic(@Body request: UserPublicRequest): Completable

    @GET("auth/profile/list")
    fun getProfileList(): Single<List<UserResponse>>
}
