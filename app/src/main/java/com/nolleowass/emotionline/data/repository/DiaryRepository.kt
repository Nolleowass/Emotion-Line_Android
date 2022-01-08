package com.nolleowass.emotionline.data.repository

import com.nolleowass.emotionline.data.api.DiaryAPI
import com.nolleowass.emotionline.data.request.diary.DiaryRequest
import com.nolleowass.emotionline.data.response.toModel
import com.nolleowass.emotionline.model.Diary
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DiaryRepository @Inject constructor(private val api: DiaryAPI) {

    fun list(userId: String): Single<List<Diary>> =
        api.list(userId).map { list ->
            list.map { it.toModel() }
        }

    fun create(content: String): Completable =
        api.create(DiaryRequest(content))

    fun update(diaryId: Int, content: String): Completable =
        api.update(diaryId, DiaryRequest(content))

    fun delete(diaryId: Int): Completable =
        api.delete(diaryId)
}