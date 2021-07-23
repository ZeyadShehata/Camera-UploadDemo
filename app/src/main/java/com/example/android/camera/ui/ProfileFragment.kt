package com.example.android.camera.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.android.camera.R
import com.example.android.camera.databinding.FragmentProfileBinding
import com.example.android.camera.utils.DialogAndSnackManager
import com.example.android.camera.utils.IMAGE_FROM_CAMERA_REQUEST
import com.example.android.camera.utils.IMAGE_FROM_GALLERY_REQUEST
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModel()
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.profileViewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        DialogAndSnackManager.setDialogBinding(requireContext(), viewModel)
        setObservers()

        if (DialogAndSnackManager.isSnackBarDismissed())
            DialogAndSnackManager.showSnackBar()




        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
            if (requestCode == IMAGE_FROM_GALLERY_REQUEST) {
                val inputStream = requireContext().contentResolver.openInputStream(data.data!!)
                val imageBitmap = BitmapFactory.decodeStream(inputStream)
                viewModel.setImageBmap(imageBitmap)
            } else if (requestCode == IMAGE_FROM_CAMERA_REQUEST) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                viewModel.setImageBmap(imageBitmap)
            }
        }

    }


    private fun setObservers() {
        /*lifecycleScope.launch {
            viewModel.addButtonClicked.collect { clicked ->
                if (clicked && activity != null) {
                    DialogAndSnackManager.showDialog()
                    viewModel.setAddButtonClicked(false)
                }
            }
        }*/
        lifecycleScope.launch {
            viewModel.cameraButtonClicked.collect { cam ->
                if (cam) {

                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                    try {
                        startActivityForResult(takePictureIntent, IMAGE_FROM_CAMERA_REQUEST)
                        DialogAndSnackManager.dismissDialog()
                    } catch (e: ActivityNotFoundException) {
                        // display error state to the user
                    }
                    viewModel.setCameraButtonClicked(false)
                }
            }

        }
//        lifecycleScope.launch {
//            viewModel.galleryButtonClicked.collect { gallery ->
//                if (gallery) {
//
//                    val pickPictureIntent = Intent(Intent.ACTION_PICK)
//                    pickPictureIntent.type = "image/*"
//                    try {
//                        startActivityForResult(
//                            pickPictureIntent,
//                            IMAGE_FROM_GALLERY_REQUEST
//                        )
//                        DialogAndSnackManager.dismissDialog()
//
//                    } catch (e: ActivityNotFoundException) {
//
//
//                    }
//                    viewModel.setGalleryButtonClicked(false)
//                }
//            }
//        }
        lifecycleScope.launch {
            viewModel.uploadSuccess.collect { success ->
                if (success) {
                    Toast.makeText(
                        activity,
                        "Upload was successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.setUploadSuccesss(false)
                }

            }
        }
        val fragment = this
        lifecycleScope.launch {
            viewModel.uploadFail.collect { success ->
                if (success) {
                    DialogAndSnackManager.createSnackBar(
                        fragment.requireView(),
                        R.string.fail,
                        viewModel
                    )
                    DialogAndSnackManager.showSnackBar()
                    viewModel.setUploadFail(false)
                }

            }
        }
        lifecycleScope.launch {
            viewModel.missingData.collect { success ->
                if (success) {
                    Toast.makeText(
                        activity,
                        "Missing File name or Image!",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.setMissingData(false)
                }

            }
        }
        lifecycleScope.launch {
            viewModel.waitingForReply.collect { success ->
                binding.submitButton.isEnabled = !success
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (DialogAndSnackManager.isSncakBarShown()) {
            DialogAndSnackManager.dismissSncakBar()
        }

    }


}


