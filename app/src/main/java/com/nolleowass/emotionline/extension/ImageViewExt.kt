package com.nolleowass.emotionline.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.nolleowass.emotionline.R

fun ImageView.load(url: String?) {
    if (url.isNullOrBlank()) return

    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_logo)
        .error(R.drawable.ic_logo)
        .into(this)
}
