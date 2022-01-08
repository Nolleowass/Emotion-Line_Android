package com.nolleowass.emotionline.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nolleowass.emotionline.databinding.ViewFeedProfileBinding
import com.nolleowass.emotionline.model.User
import com.nolleowass.emotionline.ui.feed.item.FeedProfileViewHolder

class FeedProfileAdapter : ListAdapter<User, FeedProfileViewHolder>(FeedProfileDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedProfileViewHolder {
        val binding = ViewFeedProfileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FeedProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedProfileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class FeedProfileDiffCallback: DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.imageUrl == newItem.imageUrl

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem
}
