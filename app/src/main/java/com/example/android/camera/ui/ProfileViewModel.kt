package com.example.android.camera.ui

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProfileViewModel() : ViewModel() {
    private var _addButtonClicked = MutableLiveData<Boolean>()
    val addButtonClicked: LiveData<Boolean>
        get() = _addButtonClicked
    private var _imageBitmap = MutableLiveData<Bitmap>()
    val imageBitmap: LiveData<Bitmap>
        get() = _imageBitmap


    fun setAddButtonClicked(bool: Boolean) {
        _addButtonClicked.value = bool
    }
init {
    Log.d("sss",imageBitmap.value.toString())
}
    fun setImageBmap(nBitmap: Bitmap) {
        _imageBitmap.value = nBitmap
        Log.d("sss", imageBitmap.value.toString())
    }


}