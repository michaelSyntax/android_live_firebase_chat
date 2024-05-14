package com.example.android_live_firebase_chat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.android_live_firebase_chat.MainViewModel
import com.example.android_live_firebase_chat.R
import com.example.android_live_firebase_chat.databinding.FragmentLoginBinding
import com.example.android_live_firebase_chat.utils.Debug

class LoginFragment : Fragment() {
    private lateinit var viewBinding: FragmentLoginBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
        setButtonLoginOnClickListener()
        setButtonRegisterOnClickListener()
    }

    private fun setButtonLoginOnClickListener() {
        viewBinding.btLogin.setOnClickListener {
            val email = viewBinding.tietEmail.text.toString()
            val password = viewBinding.tietPassword.text.toString()
            viewModel.login(email, password)
        }
    }

    private fun setButtonRegisterOnClickListener() {
        viewBinding.btToRegister.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
    }

    private fun addObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser != null) {
                findNavController().navigate(R.id.homeFragment)
            }
        }

        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            if (message != null) {
                showToast(message)
                viewModel.resetToastMessage()
            }
        }
    }

    private fun showToast(text: String = Debug.TOAST_EMPTY_MESSAGE_SET.value) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}