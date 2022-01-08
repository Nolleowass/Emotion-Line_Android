package com.nolleowass.emotionline.data.api

import com.nolleowass.emotionline.data.request.diary.DiaryRequest
import com.nolleowass.emotionline.data.response.DiaryResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface DiaryAPI {
    @GET("diary/list/{user_id}")
    fun list(@Path("user_id") userId: String): Single<List<DiaryResponse>>

    @POST("dairy")
    fun create(@Body request: DiaryRequest): Completable

    @PUT("diary/{diary_id}")
    fun update(
        @Path("diary_id") id: Int,
        @Body request: DiaryRequest
    ): Completable

    @DELETE("diary/{diary_id}")
    fun delete(@Path("diary_id") id: Int): Completable
}