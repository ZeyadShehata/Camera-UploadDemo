package com.example.android.camera.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.example.android.camera.R
import com.example.android.camera.databinding.DialogOptionsBinding
import com.example.android.camera.ui.ProfileViewModel
import com.google.android.material.snackbar.Snackbar

object DialogAndSnackManager {
    private lateinit var dialog: Dialog
    private  var snack : Snackbar? = null
    private var snackbarDismissed = false
    fun setDialogBinding(context: Context, profileViewModel: ProfileViewModel){
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding =
            DialogOptionsBinding.inflate(LayoutInflater.from(dialog.context))

        dialog.setContentView(dialogBinding.root)
        dialogBinding.profileViewModel = profileViewModel

    }
    fun showDialog(){
        dialog.show()
    }
    fun dismissDialog(){
        dialog.dismiss()
    }
    fun createSnackBar(v: View, s: Int, vm: ProfileViewModel){
        snack =
            Snackbar.make(
                v,
                s,
                Snackbar.LENGTH_LONG
            )
        snack?.setAction(R.string.retry) {
            vm.setUploadFail(false)
            vm.uploadPicture()
        }


    }
    fun showSnackBar(){
        snack?.show()
        snackbarDismissed = false
    }
    fun dismissSncakBar(){
        snack?.dismiss()
        snackbarDismissed = true
    }
    fun isSnackBarDismissed(): Boolean {
        return snackbarDismissed && snack != null
    }
    fun isSncakBarShown(): Boolean {
        if(snack != null){
            return snack?.isShown == true
        }
        return false
    }
}