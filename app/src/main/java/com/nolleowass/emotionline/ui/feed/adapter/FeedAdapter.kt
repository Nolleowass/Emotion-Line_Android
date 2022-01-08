package com.nolleowass.emotionline.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nolleowass.emotionline.databinding.*
import com.nolleowass.emotionline.model.Diary
import com.nolleowass.emotionline.ui.feed.item.FeedItem
import com.nolleowass.emotionline.ui.feed.item.FeedType
import com.nolleowass.emotionline.ui.feed.item.FeedViewHolder

class FeedAdapter(
    private val listener: Listener
) : ListAdapter<FeedItem, FeedViewHolder>(FeedItemDiffCallback()) {

    override fun getItemViewType(position: Int) = getItem(position).type.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder =
        when (FeedType.from(viewType)) {
            FeedType.TITLE -> {
                val binding = ViewFeedTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FeedViewHolder.TitleViewHolder(binding)
            }
            FeedType.GRAPH -> {
                val binding = ViewFeedGraphBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FeedViewHolder.GraphViewHolder(binding, listener::onClickSearch)
            }
            FeedType.DIARY -> {
                val binding = ViewFeedDiaryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FeedViewHolder.DiaryViewHolder(
                    binding,
                    listener::onClickDiaryEdit,
                    listener::onClickDiaryDelete
                )
            }
            FeedType.VISIT -> {
                val binding = ViewFeedVisitBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FeedViewHolder.VisitViewHolder(binding)
            }
            else -> {
                val binding = ViewFeedDividerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FeedViewHolder.DividerViewHolder(binding)
            }
        }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is FeedViewHolder.GraphViewHolder ->
                holder.bind(item as FeedItem.GraphItem)

            is FeedViewHolder.DiaryViewHolder ->
                holder.bind(item as FeedItem.DiaryItem)

            is FeedViewHolder.VisitViewHolder ->
                holder.bind(item as FeedItem.VisitItem)
        }
    }

    interface Listener {
        fun onClickSearch()
        fun onClickDiaryEdit(diary: Diary)
        fun onClickDiaryDelete(diaryId: Int)
    }
}

private class FeedItemDiffCallback: DiffUtil.ItemCallback<FeedItem>() {

    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        val isSameTitle = oldItem is FeedItem.TitleItem
                && newItem is FeedItem.TitleItem
                && oldItem == newItem

        val isSameGraph = oldItem is FeedItem.GraphItem
                && newItem is FeedItem.GraphItem
                && oldItem.emotionPoints.containsAll(newItem.emotionPoints)
                && oldItem.month == newItem.month

        val isSameDiary = oldItem is FeedItem.DiaryItem
                && newItem is FeedItem.DiaryItem
                && oldItem.diary == newItem.diary

        val isSameDivider = oldItem is FeedItem.DividerItem
                && newItem is FeedItem.DividerItem
                && oldItem == newItem

        val isSameVisit = oldItem is FeedItem.VisitItem
                && newItem is FeedItem.VisitItem
                && oldItem.users.map { it.accountId }.containsAll(newItem.users.map { it.accountId })

        return isSameTitle || isSameGraph || isSameDiary || isSameDivider || isSameVisit
    }

    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return oldItem == newItem
    }
}
