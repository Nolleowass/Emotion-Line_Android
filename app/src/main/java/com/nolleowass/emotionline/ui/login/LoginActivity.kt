package com.nolleowass.emotionline.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.nolleowass.emotionline.R
import com.nolleowass.emotionline.databinding.ActivityLoginBinding
import com.nolleowass.emotionline.extension.showToast
import com.nolleowass.emotionline.ui.feed.FeedActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {
        fun toIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initListener()
        initObserve()
    }

    private fun initBind() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initListener() {
        binding.btnLogin.setOnClickListener {
            viewModel.login("", "")
        }
    }

    private fun initObserve() {
        viewModel.status.observe(this) {
            it?.let {
                when (it) {
                    LoginViewStatus.LOGIN_SUCCESS -> startActivity(FeedActivity.toIntent(this))
                    LoginViewStatus.LOGIN_ERROR -> showToast(getString(R.string.error_login))
                }
            }
        }
    }
}