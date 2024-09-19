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
import com.example.android_live_firebase_chat.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    /**
     * TODO
     * 1. Button Login mit ClickLsitener und viewModel fun login call aufrufen.
     * 2. Button ToRegister mit CL navigate nach RegisterFragment
     * 3. CurrentUser aus VM beobachten und evtl. navigieren.
     */
    private lateinit var viewBinding: FragmentLoginBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentLoginBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btLogin.setOnClickListener {
            val email = viewBinding.tietEmail.text.toString()
            val password = viewBinding.tietPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            }
        }

        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if(user != null) {
                findNavController().navigate(R.id.homeFragment)
            }
        }

        viewBinding.btToRegister.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
    }
}