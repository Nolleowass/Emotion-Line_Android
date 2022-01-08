package com.nolleowass.emotionline.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nolleowass.emotionline.data.preference.SharedPreferenceConstant
import com.nolleowass.emotionline.data.preference.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _status = MutableLiveData<SplashViewStatus>()
    val status: LiveData<SplashViewStatus>
        get() = _status

    init {
        checkAutoLogin()
    }

    private fun checkAutoLogin() {
        Observable.just(isAutoLogin())
            .delay(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _status.value = if (it) SplashViewStatus.AUTO_LOGIN else SplashViewStatus.LOGIN
            }.addTo(disposable)
    }

    private fun isAutoLogin(): Boolean {
        val token = sharedPreferenceManager.getString(SharedPreferenceConstant.TOKEN_KEY)
        return token != null
    }
}