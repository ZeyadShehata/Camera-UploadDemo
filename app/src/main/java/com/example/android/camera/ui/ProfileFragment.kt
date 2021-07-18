package com.example.android.camera.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.profileViewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        DialogManager.setDialogBinding(this)
        setObservers()

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
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}