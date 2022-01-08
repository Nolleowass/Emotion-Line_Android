package com.nolleowass.emotionline.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nolleowass.emotionline.data.repository.AuthRepository
import com.nolleowass.emotionline.extension.networkOn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _status = MutableLiveData<LoginViewStatus>()
    val status: LiveData<LoginViewStatus>
        get() = _status

    fun login(id: String, password: String) {
        repository.login(id, password)
            .networkOn()
            .subscribe({
                _status.value = LoginViewStatus.LOGIN_SUCCESS
            }, {
                _status.value = LoginViewStatus.LOGIN_ERROR
            }).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}