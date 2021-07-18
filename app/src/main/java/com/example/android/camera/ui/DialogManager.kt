package com.example.android.camera.ui

import android.app.Dialog
import android.view.LayoutInflater
import android.view.Window
import com.example.android.camera.databinding.DialogOptionsBinding

object DialogManager {
    private lateinit var dialog: Dialog
    fun setDialogBinding(profileFragment:ProfileFragment){
        dialog = Dialog(profileFragment.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding =
            DialogOptionsBinding.inflate(LayoutInflater.from(dialog.context))

        dialog.setContentView(dialogBinding.root)
        dialogBinding.profileFragment = profileFragment

    }
    fun showDialog(){
        dialog.show()
    }
    fun dismissDialog(){
        dialog.dismiss()
    }
}