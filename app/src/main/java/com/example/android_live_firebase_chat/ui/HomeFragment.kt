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
import com.example.android_live_firebase_chat.adapter.UserAdapter
import com.example.android_live_firebase_chat.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var viewBinding: FragmentHomeBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
        setButtonLogoutOnClickListener()
        setupChatList()
    }

    private fun setupChatList() {
        viewBinding.rvUsers.adapter = UserAdapter(listOf(), viewModel)
    }

    private fun setButtonLogoutOnClickListener() {
       viewBinding.btLogout.setOnClickListener {
           viewModel.logout()
       }
    }

    private fun addObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser == null) {
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }
}