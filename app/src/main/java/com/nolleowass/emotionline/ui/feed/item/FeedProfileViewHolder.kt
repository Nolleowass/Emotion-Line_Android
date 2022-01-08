package com.nolleowass.emotionline.ui.feed.item

import androidx.recyclerview.widget.RecyclerView
import com.nolleowass.emotionline.databinding.ViewFeedProfileBinding
import com.nolleowass.emotionline.extension.load
import com.nolleowass.emotionline.model.User

class FeedProfileViewHolder(
    private val binding: ViewFeedProfileBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.imgProfile.load(user.imageUrl)
    }
}