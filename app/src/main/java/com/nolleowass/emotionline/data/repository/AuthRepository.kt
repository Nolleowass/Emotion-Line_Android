package com.nolleowass.emotionline.data.repository

import com.nolleowass.emotionline.data.api.AuthAPI
import com.nolleowass.emotionline.data.preference.SharedPreferenceConstant
import com.nolleowass.emotionline.data.preference.SharedPreferenceManager
import com.nolleowass.emotionline.data.request.auth.LoginRequest
import com.nolleowass.emotionline.data.request.auth.UserNameRequest
import com.nolleowass.emotionline.data.request.auth.UserPublicRequest
import com.nolleowass.emotionline.data.response.toModel
import com.nolleowass.emotionline.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthAPI,
    private val sharedPreference: SharedPreferenceManager
) {

    fun login(id: String, password: String): Completable =
        api.login(LoginRequest(id, password))
            .flatMapCompletable { response ->
                Completable.create {
                    sharedPreference.putString(SharedPreferenceConstant.TOKEN_KEY, response.token)
                    sharedPreference.putString(SharedPreferenceConstant.USER_ID_KEY, response.id)
                    it.onComplete()
                }
            }

    fun updateName(name: String): Completable =
        api.updateName(UserNameRequest(name))

    fun updatePublic(isPublic: Boolean): Completable =
        api.updatePublic(UserPublicRequest(isPublic))

    fun getProfileList(): Single<List<User>> =
        api.getProfileList().map { list ->
            list.map { it.toModel() }
        }
}
