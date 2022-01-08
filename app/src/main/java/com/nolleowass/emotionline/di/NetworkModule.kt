package com.nolleowass.emotionline.di

import com.nolleowass.emotionline.data.api.AuthAPI
import com.nolleowass.emotionline.data.api.DiaryAPI
import com.nolleowass.emotionline.data.api.ProfileAPI
import com.nolleowass.emotionline.data.interceptor.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val DEFAULT_HOST = "https://nolleowass-api.jasonchoi.dev/"
    private const val TIME_OUT = 20

    @Provides
    fun provideOkhttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(tokenInterceptor)
            .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS).build()
    }

    @Provides
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(DEFAULT_HOST)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    @Provides
    fun provideAuthAPI(retrofit: Retrofit): AuthAPI = retrofit.create(AuthAPI::class.java)

    @Provides
    fun provideDiaryAPI(retrofit: Retrofit): DiaryAPI = retrofit.create(DiaryAPI::class.java)

    @Provides
    fun provideProfileAPI(retrofit: Retrofit): ProfileAPI = retrofit.create(ProfileAPI::class.java)
}