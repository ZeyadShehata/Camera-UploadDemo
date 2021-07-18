package com.example.android.camera.ui

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.camera.network.FileAPI
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class ProfileViewModel() : ViewModel() {
    private var _addButtonClicked = MutableLiveData<Boolean>()
    val addButtonClicked: LiveData<Boolean>
        get() = _addButtonClicked
    private var _imageBitmap = MutableLiveData<Bitmap>()

    val imageBitmap: LiveData<Bitmap>
        get() = _imageBitmap

    val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String>
        get() = _fileName
    private var _cameraButtonClicked = MutableLiveData<Boolean>()
    val cameraButtonClicked: LiveData<Boolean>
        get() = _cameraButtonClicked

    private var _galleryButtonClicked = MutableLiveData<Boolean>()
    val galleryButtonClicked: LiveData<Boolean>
        get() = _galleryButtonClicked

    private var _uploadSuccess = MutableLiveData<Boolean>()
    val uploadSuccess: LiveData<Boolean>
        get() = _uploadSuccess

    private var _uploadFail = MutableLiveData<Boolean>()
    val uploadFail: LiveData<Boolean>
        get() = _uploadFail
    init {

        _fileName.value = ""
    }



    fun setAddButtonClicked(bool: Boolean) {
        _addButtonClicked.value = bool
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
                if(result.isSuccessful){

                    setUploadSuccesss(true)
                    Log.d("sss",uploadSuccess.value.toString())
                }
                else{
                    setUploadFail(true)
                }

            }
        }
    }

}