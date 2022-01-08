package com.nolleowass.emotionline.ui.write

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.nolleowass.emotionline.R
import com.nolleowass.emotionline.databinding.ActivityWriteBinding
import com.nolleowass.emotionline.extension.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WriteActivity : AppCompatActivity() {

    companion object {
        private const val DIARY_ID_KEY = "DIARY_ID_KEY"
        private const val DIARY_CONTENT_KEY = "DIARY_CONTENT_KEY"

        fun toIntent(context: Context, diaryId: Int? = null, diaryContent: String? = null) =
            Intent(context, WriteActivity::class.java)
                .putExtra(DIARY_ID_KEY, diaryId)
                .putExtra(DIARY_CONTENT_KEY, diaryContent)
    }

    private val viewModel: WriteViewModel by viewModels()

    private lateinit var binding: ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initView()
        initListener()
        initObserve()
    }

    private fun initBind() {
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initView() {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        binding.txtCreateAt.text = "${calendar.get(Calendar.MONTH).inc()}월 ${calendar.get(Calendar.DAY_OF_MONTH)}일"

        val diaryId = intent.getIntExtra(DIARY_ID_KEY, -1)
        val diaryContent = intent.getStringExtra(DIARY_CONTENT_KEY)
        if (diaryId == -1 || diaryContent == null) return

        binding.txtContent.setText(diaryContent)
        viewModel.initDiaryId(diaryId)
    }

    private fun initListener() {
        binding.txtCancel.setOnClickListener { onBackPressed() }
        binding.btnComplete.setOnClickListener {
            viewModel.writeDiary(binding.txtContent.text.toString())
        }
    }

    private fun initObserve() {
        viewModel.status.observe(this) {
            it?.let {
                when (it) {
                    WriteViewStatus.SUCCESS -> onBackPressed()
                    WriteViewStatus.ERROR -> showToast(getString(R.string.error_write_diary))
                }
            }
        }
    }
}