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
import androidx.fragment.app.viewModels
import com.example.android.camera.databinding.FragmentProfileBinding
import com.example.android.camera.utils.IMAGE_FROM_CAMERA_REQUEST
import com.example.android.camera.utils.IMAGE_FROM_GALLERY_REQUEST

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()

    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.profileViewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        DialogManager.setDialogBinding(requireContext(), viewModel)


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
        viewModel.addButtonClicked.observe(viewLifecycleOwner, { clicked ->
            if (clicked) {

                if (getActivity() != null) {
                    if (isAdded) {
                        DialogManager.showDialog()
                    }

                }

            }
        })
        viewModel.cameraButtonClicked.observe(viewLifecycleOwner, { clicked ->
            if (clicked) {

                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                try {
                    startActivityForResult(takePictureIntent, IMAGE_FROM_CAMERA_REQUEST)
                    DialogManager.dismissDialog()
                } catch (e: ActivityNotFoundException) {
                    // display error state to the user
                }
                viewModel.setCameraButtonClicked(false)

            }
        })

        viewModel.galleryButtonClicked.observe(viewLifecycleOwner, { clicked ->
            if (clicked) {

                val pickPictureIntent = Intent(Intent.ACTION_PICK)
                pickPictureIntent.type = "image/*"
                try {
                    startActivityForResult(pickPictureIntent, IMAGE_FROM_GALLERY_REQUEST)
                    DialogManager.dismissDialog()

                } catch (e: ActivityNotFoundException) {


                }
                viewModel.setGalleryButtonClicked(false)
            }
        })
        viewModel.uploadSuccess.observe(viewLifecycleOwner, { clicked ->
            if (clicked) {

                Toast.makeText(
                    activity,
                    "Upload was successful",
                    Toast.LENGTH_SHORT
                ).show()


            }
        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }
}