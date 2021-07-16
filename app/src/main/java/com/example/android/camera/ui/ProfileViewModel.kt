package com.example.android.camera.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel() : ViewModel(){
    private  var _addButtonClicked = MutableLiveData<Boolean>()
    val addButtonClicked : LiveData<Boolean>
        get() = _addButtonClicked
    fun setAddButtonClicked(){
        _addButtonClicked.value= true
    }


  /*  fun makeSourceDialog(){
        val dialog = Dialog(getApplication<Application>().applicationContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogOptionsBinding.inflate(LayoutInflater.from(dialog.context))
        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }*/
}