package com.example.android.camera.ui

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.camera.network.FileAPI
import kotlinx.coroutines.launch

class ProfileViewModel() : ViewModel() {
    private var _addButtonClicked = MutableLiveData<Boolean>()
    val addButtonClicked: LiveData<Boolean>
        get() = _addButtonClicked
    private var _imageBitmap = MutableLiveData<Bitmap>()
    private lateinit var ogBitmap: Bitmap
    val imageBitmap: LiveData<Bitmap>
        get() = _imageBitmap

    val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String>
        get() = _fileName

    fun setAddButtonClicked(bool: Boolean) {
        _addButtonClicked.value = bool
    }

    init {

        _fileName.value = ""
    }

    fun setOgBmap(oBitmap: Bitmap) {
        ogBitmap = oBitmap
        setImageBmap(ogBitmap)
    }

    fun setImageBmap(nBitmap: Bitmap) {
        _imageBitmap.value = nBitmap
        Log.d("sss", imageBitmap.value.toString())
    }

    fun setFileName(string: String) {
        _fileName.value = string
    }

    fun uploadPicture() {
        viewModelScope.launch {

            if (imageBitmap.value != null && imageBitmap.value != ogBitmap)

                FileAPI.retrofitService.uploadPhoto(
                    "inline",
                    fileName.value + ".bmp",
                    _imageBitmap.value
                )
        }
    }

}