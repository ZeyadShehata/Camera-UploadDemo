package com.example.android.camera.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
private val retrofit = Retrofit.Builder().client(okHttpClient).build()

object FileAPI {
}