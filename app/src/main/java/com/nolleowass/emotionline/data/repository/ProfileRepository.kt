package com.nolleowass.emotionline.data.repository

import com.nolleowass.emotionline.data.api.ProfileAPI
import com.nolleowass.emotionline.data.response.toModel
import com.nolleowass.emotionline.model.User
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val api: ProfileAPI) {

    fun list(): Single<List<User>> =
        api.list().map { response ->
            response.list.map { it.toModel() }
        }.flatMap {
            if (it.isNotEmpty()) Single.just(it)
            else Single.error(Throwable("Empty"))
        }
}