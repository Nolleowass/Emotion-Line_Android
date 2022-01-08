package com.nolleowass.emotionline.data.preference

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(private val application: Application) {

    private val spf = application.getSharedPreferences(SharedPreferenceConstant.SPF_NAME, 0)

    fun putString(key: String, value: String) {
        spf.edit { putString(key, value) }
    }

    fun getString(key: String): String? = spf.getString(key, null)
}
