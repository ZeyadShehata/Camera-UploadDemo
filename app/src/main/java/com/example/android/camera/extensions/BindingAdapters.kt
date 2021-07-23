package com.example.android.camera.extensions

import android.graphics.Bitmap
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.android.camera.ui.ProfileViewModel


@BindingAdapter("imageSrc")
fun ImageView.bindImage(newResponseRec: Bitmap?) {
    newResponseRec?.let {
        this.setImageBitmap(newResponseRec)
    }


}

@BindingAdapter("fileName")
fun EditText.setFileName(viewModel: ProfileViewModel) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            viewModel.setFileName(s.toString())

        }

    })
}


