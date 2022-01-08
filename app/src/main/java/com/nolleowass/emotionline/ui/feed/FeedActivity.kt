package com.nolleowass.emotionline.ui.feed

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.nolleowass.emotionline.R
import com.nolleowass.emotionline.databinding.ActivityFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedActivity : AppCompatActivity() {

    companion object {
        fun toIntent(context: Context) = Intent(context, FeedActivity::class.java)
    }

    private lateinit var binding: ActivityFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
    }

    private fun initBind() {
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCompat.finishAffinity(this)
    }
}
