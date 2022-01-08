package com.nolleowass.emotionline.ui.feed.item

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.nolleowass.emotionline.R
import com.nolleowass.emotionline.databinding.*
import com.nolleowass.emotionline.extension.toString
import com.nolleowass.emotionline.model.Emotion

sealed class FeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    class TitleViewHolder(
        private val binding: ViewFeedDiaryTitleBinding
    ) : FeedViewHolder(binding.root)

    class GraphViewHolder(
        private val binding: ViewFeedDiaryGraphBinding
    ) : FeedViewHolder(binding.root) {

        fun bind(item: FeedItem.GraphItem) {

        }
    }

    class DiaryViewHolder(
        private val binding: ViewFeedDiaryItemBinding
    ) : FeedViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(item: FeedItem.Item) {
            val emotion = Emotion.from(item.diary.emotionPoint) ?: return

            binding.txtEmotion.text = emotion.name
            binding.txtEmotionDetail.text = "${item.diary.emotionPoint}점, 멋진 하루를 보내셨네요!"
            binding.imgEmotion.setImageDrawable(getEmotionIcon(emotion))
            binding.txtContent.text = item.diary.content
            binding.txtCrateAt.text = item.diary.createAt?.toString("MM월 dd일 E요일")
        }

        private fun getEmotionIcon(emotion: Emotion): Drawable? {
            val id = when (emotion) {
                Emotion.VERY_GOOD -> R.drawable.ic_very_good
                Emotion.GOOD -> R.drawable.ic_good
                Emotion.SOSO -> R.drawable.ic_soso
                Emotion.BAD -> R.drawable.ic_bad
                Emotion.VERY_BAD -> R.drawable.ic_very_bad
            }
            return AppCompatResources.getDrawable(context, id)
        }
    }
}
