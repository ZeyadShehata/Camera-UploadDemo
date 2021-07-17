package com.example.android.camera.ui

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.camera.network.FileAPI

class ProfileViewModel() : ViewModel() {
    private var _addButtonClicked = MutableLiveData<Boolean>()
    val addButtonClicked: LiveData<Boolean>
        get() = _addButtonClicked
    private var _imageBitmap = MutableLiveData<Bitmap>()
    private var ogBitmap : Bitmap = TODO()
    val imageBitmap: LiveData<Bitmap>
        get() = _imageBitmap

    //val fileName= MutableLiveData<String>()
    fun setAddButtonClicked(bool: Boolean) {
        _addButtonClicked.value = bool
    }

    init {
        Log.d("sss", imageBitmap.value.toString())
        //fileName.value=""
    }
    fun setOgBmap(oBitmap: Bitmap){
        ogBitmap = oBitmap
        setImageBmap(ogBitmap)
    }

    fun setImageBmap(nBitmap: Bitmap) {
        _imageBitmap.value = nBitmap
        Log.d("sss", imageBitmap.value.toString())
    }

    private suspend fun uploadPicture(fileName: String) {
        if (imageBitmap.value != null && imageBitmap.value!= ogBitmap)
            FileAPI.retrofitService.uploadPhoto("inline", fileName + ".bmp", _imageBitmap.value)
    }

}