package com.nolleowass.emotionline.ui.feed.item

import com.nolleowass.emotionline.model.Diary
import com.nolleowass.emotionline.model.User

enum class FeedType {
    TITLE,
    GRAPH,
    DIARY,
    DIVIDER,
    VISIT;

    companion object {
        fun from(ordinal: Int) = values().find { it.ordinal == ordinal }
    }
}

sealed class FeedItem {

    abstract val type: FeedType

    object TitleItem : FeedItem() {
        override val type = FeedType.TITLE
    }

    data class GraphItem(val emotionPoints: List<Int>, val month: Int) : FeedItem() {
        override val type = FeedType.GRAPH
    }

    data class DiaryItem(val diary: Diary) : FeedItem() {
        override val type = FeedType.DIARY
    }

    object DividerItem : FeedItem() {
        override val type = FeedType.DIVIDER
    }

    data class VisitItem(val users: List<User>) : FeedItem() {
        override val type = FeedType.VISIT
    }
}
