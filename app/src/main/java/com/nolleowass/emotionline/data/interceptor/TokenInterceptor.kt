package com.nolleowass.emotionline.data.interceptor

import com.nolleowass.emotionline.data.preference.SharedPreferenceConstant
import com.nolleowass.emotionline.data.preference.SharedPreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    sharedPreferenceManager: SharedPreferenceManager
) : Interceptor {

    private val token = sharedPreferenceManager.getString(SharedPreferenceConstant.TOKEN_KEY)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            if (token != null) chain.request().newBuilder()
                .header("token", token).build()
            else chain.request()

        return chain.proceed(request)
    }
}