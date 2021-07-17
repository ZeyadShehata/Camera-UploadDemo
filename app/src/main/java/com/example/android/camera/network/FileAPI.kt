package com.example.android.camera.network

import com.example.android.camera.utils.FILE_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
private val retrofit = Retrofit.Builder().baseUrl(FILE_BASE_URL).client(okHttpClient).build()

object FileAPI {
    val retrofitService: FileUploadService by lazy { retrofit.create(FileUploadService::class.java)}
}