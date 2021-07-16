package com.example.android.camera.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.camera.databinding.DialogOptionsBinding
import com.example.android.camera.databinding.FragmentProfileBinding

class ProfileFragment: Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private  val viewModel: ProfileViewModel by viewModels()
    private val binding
            get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.profileViewModel = viewModel
        viewModel.addButtonClicked.observe(viewLifecycleOwner,{ clicked ->
            if(clicked){
                val dialog = Dialog(requireContext())
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                val dialogBinding = DialogOptionsBinding.inflate(LayoutInflater.from(dialog.context))

                dialog.setContentView(dialogBinding.root)
                dialogBinding.profileFragment = this
                dialog.show()
            }
        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}