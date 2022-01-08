package com.nolleowass.emotionline.ui.feed.item

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.nolleowass.emotionline.R
import com.nolleowass.emotionline.databinding.*
import com.nolleowass.emotionline.extension.toString
import com.nolleowass.emotionline.model.Diary
import com.nolleowass.emotionline.model.Emotion
import com.nolleowass.emotionline.ui.feed.adapter.FeedProfileAdapter

sealed class FeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    class TitleViewHolder(
        private val binding: ViewFeedTitleBinding
    ) : FeedViewHolder(binding.root)

    class GraphViewHolder(
        private val binding: ViewFeedGraphBinding,
        private val onClickSearch: () -> Unit
    ) : FeedViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(item: FeedItem.GraphItem) {
            binding.txtMonth.text = "${item.month}월"
            binding.txtSearch.setOnClickListener { onClickSearch.invoke() }

            setChart(binding.chart)
            addEntry(binding.chart, item.emotionPoints)
        }

        private fun addEntry(lineChart: LineChart, dataList: List<Int>) {
            val data = lineChart.data

            data?.let {
                var set = data.getDataSetByIndex(0)

                if (set == null) {
                    set = createSet()
                    data.addDataSet(set)
                }

                dataList.forEach {
                    data.addEntry(Entry(set.entryCount.toFloat(), it.toFloat()), 0)
                }

                data.notifyDataChanged()
                lineChart.apply {
                    notifyDataSetChanged()
                    setPinchZoom(false)
                    isDoubleTapToZoomEnabled = false
                    isDragEnabled = false
                    description.isEnabled = false
                    isHighlightPerTapEnabled = false
                }
            }
        }

        private fun createSet(): LineDataSet {
            val primaryColor = ContextCompat.getColor(context, R.color.primary)

            val set = LineDataSet(null, null).apply {
                color = primaryColor
                setCircleColor(primaryColor)
                fillColor = primaryColor
                highLightColor = primaryColor
                valueTextSize = 15f
                lineWidth = 2f
                circleRadius = 3f
                fillAlpha = 0
                setDrawValues(false)
            }
            return set
        }

        private fun setChart(lineChart: LineChart) {
            lineChart.apply {
                xAxis.isEnabled = false
                axisRight.isEnabled = false
                axisLeft.isEnabled = false
                legend.isEnabled = false
            }

            val lineData = LineData()
            lineChart.data = lineData
        }
    }

    class DiaryViewHolder(
        private val binding: ViewFeedDiaryBinding,
        private val onClickEdit: (Diary) -> Unit,
        private val onClickDelete: (Int) -> Unit
    ) : FeedViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(item: FeedItem.DiaryItem) {
            val emotion = Emotion.from(item.diary.emotionPoint) ?: return

            binding.txtEmotion.text = emotion.name
            binding.txtEmotionDetail.text = "${item.diary.emotionPoint}점, 멋진 하루를 보내셨네요!"
            binding.imgEmotion.setImageDrawable(getEmotionIcon(emotion))
            binding.txtContent.text = item.diary.content
            binding.txtCrateAt.text = item.diary.createAt?.toString("MM월 dd일 E요일")

            binding.btnEdit.setOnClickListener {
                onClickEdit.invoke(item.diary)
            }
            binding.btnDelete.setOnClickListener {
                onClickDelete.invoke(item.diary.id)
            }
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

    class DividerViewHolder(
        private val binding: ViewFeedDividerBinding
    ) : FeedViewHolder(binding.root)

    class VisitViewHolder(
        private val binding: ViewFeedVisitBinding
    ) : FeedViewHolder(binding.root) {

        companion object {
            private const val MAX_COUNT = 3
        }

        private val adapter = FeedProfileAdapter()

        init {
            binding.list.adapter = adapter
        }

        fun bind(item: FeedItem.VisitItem) {
            val count = item.users.size
            var names = ""

            item.users
                .take(MAX_COUNT)
                .forEachIndexed { index, user ->
                    names += user.name
                    if (index != count.dec()) names += ","
                }

            binding.txtVisitDetail.text =
                if (count >= MAX_COUNT) "{$names}님 외 ${count - MAX_COUNT}명이 방문했어요."
                else "${names}님이 방문했어요."

            adapter.submitList(item.users.toMutableList())
        }
    }
}
