package com.example.android.camera.model

import com.squareup.moshi.Json

data class UploadResponseModel (
    @Json(name = "FileId")
    val fileId: String,
    @Json(name = "FileName")
    val fileName: String,
    @Json(name = "FileExt")
    val fileExt:String,
    @Json(name = "Url")
    val url: String
        )
