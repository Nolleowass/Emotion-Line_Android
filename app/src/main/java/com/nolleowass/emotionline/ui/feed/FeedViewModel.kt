package com.nolleowass.emotionline.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nolleowass.emotionline.data.preference.SharedPreferenceConstant
import com.nolleowass.emotionline.data.preference.SharedPreferenceManager
import com.nolleowass.emotionline.data.repository.DiaryRepository
import com.nolleowass.emotionline.extension.networkOn
import com.nolleowass.emotionline.ui.feed.item.FeedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: DiaryRepository,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _status = MutableLiveData<FeedViewStatus>()
    val status: LiveData<FeedViewStatus>
        get() = _status

    private val userId: String?
        get() = sharedPreferenceManager.getString(SharedPreferenceConstant.USER_NAME_KEY)

    private val _feedDiaryItems = ArrayList<FeedItem>()
    val feedItems: List<FeedItem>
        get() = _feedDiaryItems.toMutableList()

    init {
        loadDiaries()
    }

    private fun loadDiaries() {
        val userId = this.userId
            ?: let {
                _status.value = FeedViewStatus.FEED_ERROR
                return
            }

        _feedDiaryItems.add(FeedItem.TitleItem)

        repository.list(userId)
            .networkOn()
            .subscribe({ diaries ->
                _feedDiaryItems.add(FeedItem.GraphItem(diaries))
                _feedDiaryItems.add(FeedItem.Item(diaries.last()))
                _status.value = FeedViewStatus.FEED_LOADED
            }, {
                _status.value = FeedViewStatus.FEED_ERROR
            }).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}