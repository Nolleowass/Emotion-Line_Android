package com.nolleowass.emotionline.extension

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T: Any> Single<T>.networkOn() =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun Completable.networkOn() =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())