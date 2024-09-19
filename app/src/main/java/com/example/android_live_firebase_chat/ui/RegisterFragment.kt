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
    /**
     * TODO:
     * 1. Button Back CL navigate to LoginFragment.
     * 2. Button Register CL rufe register fun im VM auf.
     * 3. CurrentUser observe und zum HomeFragment navigieren.
     */
    private lateinit var viewBinding: FragmentRegisterBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentRegisterBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btBackToLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        viewBinding.btRegister.setOnClickListener {
            val email = viewBinding.tietEmailRegister.text.toString()
            val password = viewBinding.tietPasswordRegister.text.toString()
            val username = viewBinding.tietUsername.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
                viewModel.register(email, password, username)
            }
        }

        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }
}