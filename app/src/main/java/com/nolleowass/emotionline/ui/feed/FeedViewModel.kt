package com.nolleowass.emotionline.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nolleowass.emotionline.data.preference.SharedPreferenceConstant
import com.nolleowass.emotionline.data.preference.SharedPreferenceManager
import com.nolleowass.emotionline.data.repository.DiaryRepository
import com.nolleowass.emotionline.data.repository.ProfileRepository
import com.nolleowass.emotionline.extension.networkOn
import com.nolleowass.emotionline.extension.toString
import com.nolleowass.emotionline.ui.feed.item.FeedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val profileRepository: ProfileRepository,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _status = MutableLiveData<FeedViewStatus>()
    val status: LiveData<FeedViewStatus>
        get() = _status

    private val userId: String?
        get() = sharedPreferenceManager.getString(SharedPreferenceConstant.USER_ID_KEY)

    private val _feedDiaryItems = ArrayList<FeedItem>()
    val feedItems: List<FeedItem>
        get() = _feedDiaryItems.toMutableList()

    private val calendar = Calendar.getInstance(TimeZone.getDefault())

    private var currentYear: Int = calendar.get(Calendar.YEAR)
    private var currentMonth: Int = calendar.get(Calendar.MONTH).inc()
    private var currentDay: Int = calendar.get(Calendar.DAY_OF_MONTH)

    fun initDate(year: Int, month: Int, day: Int) {
        currentYear = year
        currentMonth = month.inc()
        currentDay = day

        loadDiaries()
    }

    fun loadDiaries() {
        val userId = this.userId
            ?: let {
                _status.value = FeedViewStatus.FEED_ERROR
                return
            }

        _feedDiaryItems.clear()
        _feedDiaryItems.add(FeedItem.TitleItem)
        _status.value = FeedViewStatus.FEED_LOADED

        diaryRepository.list(userId, currentYear, currentMonth)
            .networkOn()
            .subscribe({ diaries ->
                _feedDiaryItems.add(FeedItem.GraphItem(diaries.map { it.emotionPoint }, currentMonth))
                diaries.findLast { it.createAt?.toString("dd")?.toInt() == currentDay }
                    ?.let { _feedDiaryItems.add(FeedItem.DiaryItem(it)) }

                _status.value = FeedViewStatus.FEED_LOADED
                loadProfiles()
            }, {
                _status.value = FeedViewStatus.FEED_ERROR
            }).addTo(disposable)
    }

    private fun loadProfiles() {
        profileRepository.list()
            .networkOn()
            .subscribe({
                _feedDiaryItems.add(FeedItem.DividerItem)
                _feedDiaryItems.add(FeedItem.VisitItem(it))

                _status.value = FeedViewStatus.FEED_LOADED
            }, {
                _status.value = FeedViewStatus.FEED_ERROR
            }).addTo(disposable)
    }

    fun deleteDiary(diaryId: Int) {
        diaryRepository.delete(diaryId)
            .networkOn()
            .subscribe({
                _status.value = FeedViewStatus.DIARY_DELETE
            }, {
                _status.value = FeedViewStatus.DIARY_ERROR
            }).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}