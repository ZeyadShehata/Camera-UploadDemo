package com.example.android.camera.network

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class FileUploadRepo {

    suspend fun getPhoto(fileName: String, bitmapdata: ByteArray): Boolean {
        try {
            val result = FileAPI.retrofitService.uploadPhoto(
                "inline",
                "multipart/form-data",
                "------7MA4YWxkTrZu0gW",
                "attachment",
                "FileData",
                "--FILE DATA--",
                fileName + ".bmp",
                bitmapdata.toRequestBody("image/*".toMediaType())

            )
            if (result.isSuccessful) {
                return true
            } else
                return false

        } catch(e: Exception){
            return false
        }
    }
}