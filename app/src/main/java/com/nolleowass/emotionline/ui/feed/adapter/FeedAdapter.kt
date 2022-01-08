package com.nolleowass.emotionline.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nolleowass.emotionline.databinding.*
import com.nolleowass.emotionline.ui.feed.item.FeedItem
import com.nolleowass.emotionline.ui.feed.item.FeedType
import com.nolleowass.emotionline.ui.feed.item.FeedViewHolder

class FeedAdapter : ListAdapter<FeedItem, FeedViewHolder>(DiffItemCallback()) {

    override fun getItemViewType(position: Int) = getItem(position).type.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder =
        when (FeedType.from(viewType)) {
            FeedType.TITLE -> {
                val binding = ViewFeedDiaryTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FeedViewHolder.TitleViewHolder(binding)
            }
            FeedType.GRAPH -> {
                val binding = ViewFeedDiaryGraphBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FeedViewHolder.GraphViewHolder(binding)
            }
            else -> {
                val binding = ViewFeedDiaryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FeedViewHolder.DiaryViewHolder(binding)
            }
        }


    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is FeedViewHolder.GraphViewHolder ->
                holder.bind(item as FeedItem.GraphItem)

            is FeedViewHolder.DiaryViewHolder ->
                holder.bind(item as FeedItem.Item)
        }
    }
}

private class DiffItemCallback: DiffUtil.ItemCallback<FeedItem>() {

    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        val isSameGraph = oldItem is FeedItem.GraphItem
                && newItem is FeedItem.GraphItem
                && oldItem.diaries.map { it.id }.containsAll(newItem.diaries.map { it.id })

        val isSameDiary = oldItem is FeedItem.Item
                && newItem is FeedItem.Item
                && oldItem.diary.id == newItem.diary.id

        return isSameGraph || isSameDiary
    }

    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return oldItem == newItem
    }
}
