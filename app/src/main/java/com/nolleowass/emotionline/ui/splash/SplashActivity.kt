package com.nolleowass.emotionline.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.nolleowass.emotionline.R
import com.nolleowass.emotionline.databinding.ActivitySplashBinding
import com.nolleowass.emotionline.ui.feed.FeedActivity
import com.nolleowass.emotionline.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
    }

    private fun initBind() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initObserve() {
        viewModel.status.observe(this) {
            it?.let {
                when (it) {
                    SplashViewStatus.LOGIN -> startActivity(LoginActivity.toIntent(this))
                    SplashViewStatus.AUTO_LOGIN -> startActivity(FeedActivity.toIntent(this))
                }
            }
        }
    }
}