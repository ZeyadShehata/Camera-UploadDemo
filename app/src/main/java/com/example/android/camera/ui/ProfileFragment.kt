package com.example.android.camera.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.camera.databinding.DialogOptionsBinding
import com.example.android.camera.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()
    lateinit var dialog: Dialog
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
        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding =
            DialogOptionsBinding.inflate(LayoutInflater.from(dialog.context))

        dialog.setContentView(dialogBinding.root)
        dialogBinding.profileFragment = this
        dialogBinding.profileViewModel = viewModel
        setObservers()

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("sss", "resultCode.toString()")
        if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
            if (requestCode == 1) {
                Log.d("sss", resultCode.toString())
                Log.d("sss", AppCompatActivity.RESULT_OK.toString())
                val inputStream = requireContext().contentResolver.openInputStream(data.data!!)

                val imageBitmap = BitmapFactory.decodeStream(inputStream)
                viewModel.setImageBmap(imageBitmap)
            } else if (requestCode == 2) {
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
                        Log.d("sss", "acttttt")
                        dialog.show()
                    }

                }

            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}