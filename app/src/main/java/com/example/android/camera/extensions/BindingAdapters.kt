package com.example.android.camera.extensions

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.android.camera.ui.ProfileFragment

const val CAMERA_REQUEST_CODE = 1

@BindingAdapter("image_from_cam_fragment")
fun Button.openCamera(fragment: ProfileFragment) {
    this.setOnClickListener {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        try {
            Log.d("sss","ricooo")
            fragment.startActivityForResult(takePictureIntent, 1)
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

    this.setImageBitmap(newResponseRec)


}

