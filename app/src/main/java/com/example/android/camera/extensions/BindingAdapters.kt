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
import com.example.android.camera.utils.IMAGE_FROM_CAMERA_REQUEST
import com.example.android.camera.utils.IMAGE_FROM_GALLERY_REQUEST


@BindingAdapter("image_from_cam_fragment")
fun Button.openCamera(fragment: ProfileFragment) {
    this.setOnClickListener {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        try {
            fragment.startActivityForResult(takePictureIntent, IMAGE_FROM_CAMERA_REQUEST)
            if (fragment.dialog != null)
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
            fragment.startActivityForResult(pickPictureIntent, IMAGE_FROM_GALLERY_REQUEST)
            if (fragment.dialog != null)
                fragment.dialog.dismiss()

        } catch (e: ActivityNotFoundException) {


        }
    }
}

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


