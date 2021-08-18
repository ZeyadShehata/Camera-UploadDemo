package com.example.android.camera.ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.camera.network.FileUploadRepo
import com.example.android.camera.utils.CoroutineContextProvider
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class ProfileViewModel(private val repo: FileUploadRepo, dispatcher: CoroutineContextProvider) :
    ViewModel() {
    // private val repo = FileUploadRepo()

    private var _imageBitmap = MutableLiveData<Bitmap>()
    val imageBitmap: LiveData<Bitmap>
        get() = _imageBitmap

    private val _fileName = MutableLiveData("")
    val fileName: LiveData<String>
        get() = _fileName


    private var _addButtonClicked = MutableLiveData<Boolean>()
    val addButtonClicked: LiveData<Boolean>
        get() = _addButtonClicked

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

    private var _missingData = MutableLiveData<Boolean>()
    val missingData: LiveData<Boolean>
        get() = _missingData

    private var _waitingForReply = MutableLiveData<Boolean>()
    val waitingForReply: LiveData<Boolean>
        get() = _waitingForReply


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
                val result = repo.getPhoto(fileName.value.toString(), bitmapdata)

                if (result) {
                    setUploadFail(false)
                    setUploadSuccesss(true)
                } else {
                    setUploadFail(true)
                    setUploadSuccesss(false)
                }


            } else {
                setMissingData(true)
            }
            setWaitingForReply(false)
        }
    }

}

