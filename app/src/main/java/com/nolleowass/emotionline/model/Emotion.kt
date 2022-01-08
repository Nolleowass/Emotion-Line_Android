package com.nolleowass.emotionline.model

enum class Emotion(val value: Int) {
    VERY_GOOD(80),
    GOOD(60),
    SOSO(40),
    BAD(20),
    VERY_BAD(0);

    companion object {
        fun from(point: Int) = values().find { point >= it.value }
    }
}