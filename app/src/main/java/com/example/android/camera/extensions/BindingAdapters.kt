package com.example.android.camera.extensions

import android.content.ActivityNotFoundException
import android.content.Intent
import android.provider.MediaStore
import android.widget.Button
import androidx.databinding.BindingAdapter
import com.example.android.camera.ui.ProfileFragment

@BindingAdapter("image_from_cam")
fun Button.openCamera(fragment: ProfileFragment){
    this.setOnClickListener{
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            fragment.startActivityForResult(takePictureIntent, 1)

        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }
}

