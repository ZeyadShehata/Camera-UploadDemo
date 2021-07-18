package com.example.android.camera.network

import com.example.android.camera.model.UploadResponseModel
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface FileUploadService {
    @POST("upload")
    suspend fun uploadPhoto(
        @Query("Content-Disposition:") method: String,
        @Query("Content-Type")contentType: String,
        @Query("boundary")boundary: String,
        @Query("Content-Disposition:") method2: String,
        @Query("name")partName: String,
        @Query("Content-Type")contentType2: String,
        @Query("filename") name2: String,
        @Body pic: RequestBody
    ):  Response<UploadResponseModel>
}