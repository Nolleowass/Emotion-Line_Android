package com.nolleowass.emotionline.ui.feed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nolleowass.emotionline.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
    }
}
