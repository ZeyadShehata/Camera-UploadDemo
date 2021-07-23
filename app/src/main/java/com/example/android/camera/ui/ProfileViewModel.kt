package com.example.android.camera.ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.camera.network.FileUploadRepo
import com.example.android.camera.utils.CoroutineContextProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class ProfileViewModel(private val repo: FileUploadRepo, dispatcher: CoroutineContextProvider) :
    ViewModel() {
    // private val repo = FileUploadRepo()

    private var _imageBitmap = MutableLiveData<Bitmap>()
    val imageBitmap: LiveData<Bitmap>
        get() = _imageBitmap

    private val _fileName = MutableStateFlow("")
    val fileName: StateFlow<String> = _fileName


    private var _addButtonClicked = MutableLiveData<Boolean>()
    val addButtonClicked: LiveData<Boolean> = _addButtonClicked

    private var _cameraButtonClicked = MutableStateFlow(false)
    val cameraButtonClicked: StateFlow<Boolean> = _cameraButtonClicked

    private var _galleryButtonClicked = MutableLiveData<Boolean>()
    val galleryButtonClicked: LiveData<Boolean> = _galleryButtonClicked

    private var _uploadSuccess = MutableStateFlow(false)
    val uploadSuccess: StateFlow<Boolean> = _uploadSuccess

    private var _uploadFail = MutableStateFlow(false)
    val uploadFail: StateFlow<Boolean> = _uploadFail

    private var _missingData = MutableStateFlow(false)
    val missingData: StateFlow<Boolean> = _missingData

    private var _waitingForReply = MutableStateFlow(false)
    val waitingForReply: StateFlow<Boolean> = _waitingForReply


    init {

        _fileName.value = ""

    }


    fun setAddButtonClicked(bool: Boolean) {
        _addButtonClicked.value = bool
    }

    fun setWaitingForReply(bool: Boolean) {
        _waitingForReply.value = bool
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
        setWaitingForReply(true)
        viewModelScope.launch {

            if (imageBitmap.value != null && _fileName.value != "") {
                setMissingData(false)

                val bos = ByteArrayOutputStream()
                imageBitmap.value!!.compress(Bitmap.CompressFormat.PNG, 90, bos)
                val bitmapdata = bos.toByteArray()
                val result = repo.getPhoto(fileName.value, bitmapdata)

                if (result) {
                    setUploadFail(false)
                    setUploadSuccesss(true)
                } else {
                    setUploadFail(true)

                }


            } else {
                setMissingData(true)
            }
            setWaitingForReply(false)
        }
    }

}

