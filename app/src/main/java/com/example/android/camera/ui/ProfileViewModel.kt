package com.example.android.camera.ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.camera.network.FileAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class ProfileViewModel() : ViewModel() {
    private var _addButtonClicked = MutableStateFlow(false)
    val addButtonClicked: StateFlow<Boolean> = _addButtonClicked

    private var _imageBitmap = MutableLiveData<Bitmap>()
    val imageBitmap: LiveData<Bitmap>
        get() = _imageBitmap

    val _fileName = MutableStateFlow("")
    val fileName: StateFlow<String> = _fileName


    private var _cameraButtonClicked = MutableStateFlow(false)
    val cameraButtonClicked: StateFlow<Boolean> = _cameraButtonClicked

    private var _galleryButtonClicked = MutableStateFlow(false)
    val galleryButtonClicked: StateFlow<Boolean> = _galleryButtonClicked

    private var _uploadSuccess = MutableStateFlow(false)
    val uploadSuccess: StateFlow<Boolean> = _uploadSuccess

    private var _uploadFail = MutableStateFlow(false)
    val uploadFail:StateFlow<Boolean>  = _uploadFail

    private var _missingData = MutableStateFlow(false)
    val missingData:StateFlow<Boolean>  = _missingData
    init {

        _fileName.value = ""
        viewModelScope.launch {
            _addButtonClicked.value = false

        }
        }


    fun setAddButtonClicked(bool: Boolean) {
        _addButtonClicked.value = bool
    }
    fun setMissingData(bool: Boolean) {
        _missingData.value = bool
    }
    fun setCameraButtonClicked(bool: Boolean) {

        _cameraButtonClicked.value = bool

    }

    fun setGalleryButtonClicked(bool: Boolean) {
        _galleryButtonClicked.value = bool
    }

    fun setUploadSuccesss(bool: Boolean) {
        _uploadSuccess.value = bool
    }

    fun setUploadFail(bool: Boolean) {
        _uploadFail.value = bool
    }

    fun setImageBmap(nBitmap: Bitmap) {
        _imageBitmap.value = nBitmap
    }

    fun setFileName(string: String) {
        _fileName.value = string
    }

    fun uploadPicture() {

        viewModelScope.launch {

            if (imageBitmap.value != null && _fileName.value != "") {
                setMissingData(false)
                val bos = ByteArrayOutputStream()
                imageBitmap.value!!.compress(Bitmap.CompressFormat.PNG, 90, bos)
                val bitmapdata = bos.toByteArray()

                val result = FileAPI.retrofitService.uploadPhoto(
                    "inline",
                    "multipart/form-data",
                    "------7MA4YWxkTrZu0gW",
                    "attachment",
                    "FileData",
                    "--FILE DATA--",
                    fileName.value + ".bmp",
                    bitmapdata.toRequestBody("image/*".toMediaType())

                )
                if (result.isSuccessful) {
                    setUploadSuccesss(true)
                } else {
                    setUploadFail(true)
                }


            }
            else{
                setMissingData(true)
            }
        }
    }

}

