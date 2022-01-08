package com.nolleowass.emotionline.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nolleowass.emotionline.data.preference.SharedPreferenceConstant
import com.nolleowass.emotionline.data.preference.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    private val _status = MutableLiveData<SplashViewStatus>()
    val status: LiveData<SplashViewStatus>
        get() = _status

    init {
        checkAutoLogin()
    }

    private fun checkAutoLogin() {
        _status.value = if (isAutoLogin()) SplashViewStatus.AUTO_LOGIN else SplashViewStatus.LOGIN
    }

    private fun isAutoLogin(): Boolean {
        val token = sharedPreferenceManager.getString(SharedPreferenceConstant.TOKEN_KEY)
        return token != null
    }
}