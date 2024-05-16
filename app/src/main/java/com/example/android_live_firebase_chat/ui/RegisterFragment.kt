package com.example.android_live_firebase_chat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.android_live_firebase_chat.MainViewModel
import com.example.android_live_firebase_chat.R
import com.example.android_live_firebase_chat.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var viewBinding: FragmentRegisterBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonsOnClickListener()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { authResult ->
            if (authResult != null) {
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }

    private fun setButtonsOnClickListener() {
        setRegisterButtonOnClickListener()
        setBackButtonOnClickListener()
    }

    private fun setBackButtonOnClickListener() {
        viewBinding.btBackToLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun setRegisterButtonOnClickListener() {
        viewBinding.btRegister.setOnClickListener {
            val email = viewBinding.tietEmailRegister.text.toString()
            val password = viewBinding.tietPasswordRegister.text.toString()
            val username = viewBinding.tietUsername.text.toString()

            viewModel.register(email, password, username)
        }
    }
}