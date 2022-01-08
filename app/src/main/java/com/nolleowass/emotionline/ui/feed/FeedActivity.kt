package com.nolleowass.emotionline.ui.feed

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.nolleowass.emotionline.R
import com.nolleowass.emotionline.databinding.ActivityFeedBinding
import com.nolleowass.emotionline.extension.showToast
import com.nolleowass.emotionline.ui.feed.adapter.FeedAdapter
import dagger.hilt.android.AndroidEntryPoint
import android.app.DatePickerDialog
import com.nolleowass.emotionline.model.Diary
import com.nolleowass.emotionline.ui.write.WriteActivity
import java.util.*

@AndroidEntryPoint
class FeedActivity : AppCompatActivity() {

    companion object {
        fun toIntent(context: Context) = Intent(context, FeedActivity::class.java)
    }

    private val viewModel: FeedViewModel by viewModels()

    private lateinit var binding: ActivityFeedBinding

    private lateinit var adapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initListener()
        initAdapter()
        initObserve()
    }

    private fun initBind() {
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initListener() {
        binding.btnWrite.setOnClickListener {
            startActivity(WriteActivity.toIntent(this))
        }
    }

    private fun initAdapter() {
        adapter = FeedAdapter(object : FeedAdapter.Listener {
            override fun onClickSearch() {
                val cal: Calendar = Calendar.getInstance(TimeZone.getDefault())

                DatePickerDialog(this@FeedActivity, { _, year, month, day ->
                    viewModel.initDate(year, month, day)
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
            }

            override fun onClickDiaryEdit(diary: Diary) {
                startActivity(WriteActivity.toIntent(this@FeedActivity, diary.id, diary.content))
            }

            override fun onClickDiaryDelete(diaryId: Int) {
                viewModel.deleteDiary(diaryId)
            }
        })
        binding.list.adapter = adapter
    }

    private fun initObserve() {
        viewModel.status.observe(this) {
            it?.let {
                when (it) {
                    FeedViewStatus.FEED_LOADED ->
                        adapter.submitList(viewModel.feedItems)

                    FeedViewStatus.FEED_ERROR ->
                        showToast(getString(R.string.error_load_feed))

                    FeedViewStatus.DIARY_DELETE ->
                        viewModel.loadDiaries()

                    FeedViewStatus.DIARY_ERROR ->
                        showToast(getString(R.string.error_delete_diary))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadDiaries()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCompat.finishAffinity(this)
    }
}
