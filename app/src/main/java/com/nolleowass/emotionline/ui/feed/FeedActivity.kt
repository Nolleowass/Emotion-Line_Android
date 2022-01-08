package com.nolleowass.emotionline.ui.feed

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.ConcatAdapter
import com.nolleowass.emotionline.R
import com.nolleowass.emotionline.databinding.ActivityFeedBinding
import com.nolleowass.emotionline.extension.showToast
import com.nolleowass.emotionline.ui.feed.adapter.FeedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedActivity : AppCompatActivity() {

    companion object {
        fun toIntent(context: Context) = Intent(context, FeedActivity::class.java)
    }

    private val viewModel: FeedViewModel by viewModels()

    private lateinit var binding: ActivityFeedBinding

    private lateinit var adapter: ConcatAdapter
    private lateinit var feedAdapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initAdapter()
        initObserve()
    }

    private fun initBind() {
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initAdapter() {
        feedAdapter = FeedAdapter()
        adapter = ConcatAdapter(feedAdapter)
        binding.list.adapter = adapter
    }

    private fun initObserve() {
        viewModel.status.observe(this) {
            it?.let {
                when (it) {
                    FeedViewStatus.FEED_LOADED ->
                        feedAdapter.submitList(viewModel.feedItems)

                    FeedViewStatus.FEED_ERROR ->
                        showToast(getString(R.string.error_feed_dairy))
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCompat.finishAffinity(this)
    }
}
