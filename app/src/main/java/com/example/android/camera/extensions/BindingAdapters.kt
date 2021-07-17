package com.example.android.camera.extensions

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.android.camera.ui.ProfileFragment
import com.example.android.camera.ui.ProfileViewModel

const val CAMERA_REQUEST_CODE = 1

@BindingAdapter("image_from_cam_fragment")
fun Button.openCamera(fragment: ProfileFragment) {
    this.setOnClickListener {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        try {
            fragment.startActivityForResult(takePictureIntent, 2)
            if(fragment.dialog != null)
                fragment.dialog.dismiss()
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }
}


@BindingAdapter("image_from_gallery")
fun Button.openGallery(fragment: ProfileFragment) {
    this.setOnClickListener {
        val pickPictureIntent = Intent(Intent.ACTION_PICK)
        pickPictureIntent.type = "image/*"
        try {
            fragment.startActivityForResult(pickPictureIntent, 1)
            if(fragment.dialog != null)
                fragment.dialog.dismiss()

        } catch (e: ActivityNotFoundException) {


        }
    }
}

@BindingAdapter("imageSrc")
fun ImageView.bindImage(newResponseRec: Bitmap) {
    //optimize(Zek) change [newResponseRec] to nullable and use this?.let { to set Image Bitmap}
    this.setImageBitmap(newResponseRec)


}

@BindingAdapter("fileName")
fun EditText.setFileName(viewModel: ProfileViewModel){
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


