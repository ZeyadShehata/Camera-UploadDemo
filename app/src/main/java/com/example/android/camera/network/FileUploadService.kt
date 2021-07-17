package com.example.android.camera.network

import retrofit2.http.GET
import retrofit2.http.Query

interface FileUploadService {
    @GET("upload")
    suspend fun UploadPhoto(
        @Query("Content-Disposition:") method: String,
        @Query("filename") name: String
    )
}