package com.example.android.camera.network

import com.example.android.camera.utils.FILE_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
private val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(FILE_BASE_URL).client(okHttpClient).build()

object FileAPI {
    val retrofitService: FileUploadApi by lazy { retrofit.create(FileUploadApi::class.java)}
}
