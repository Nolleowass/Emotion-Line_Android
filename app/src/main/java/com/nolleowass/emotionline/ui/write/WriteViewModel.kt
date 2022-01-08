package com.nolleowass.emotionline.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nolleowass.emotionline.data.repository.DiaryRepository
import com.nolleowass.emotionline.extension.networkOn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val repository: DiaryRepository
) : ViewModel() {

    private val _status = MutableLiveData<WriteViewStatus>()
    val status: LiveData<WriteViewStatus>
        get() = _status

    private val disposable = CompositeDisposable()

    private var diaryId: Int? = null

    fun initDiaryId(diaryId: Int) {
        this.diaryId = diaryId
    }

    fun writeDiary(content: String) {
        diaryId?.let { updateDiary(it, content) }
            ?: repository.create(content)
                .networkOn()
                .subscribe({
                    _status.value = WriteViewStatus.SUCCESS
                }, {
                    _status.value = WriteViewStatus.ERROR
                }).addTo(disposable)
    }

    private fun updateDiary(diaryId: Int, content: String) {
        repository.update(diaryId, content)
            .networkOn()
            .subscribe({
                _status.value = WriteViewStatus.SUCCESS
            }, {
                _status.value = WriteViewStatus.ERROR
            }).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}