package com.example.android.camera.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.File

interface FileUploadService {
    @POST("upload")
    suspend fun uploadPhoto(
        @Query("Content-Disposition:") method: String,
        @Query("filename") name: String,
        @Body pic: File
    )
}