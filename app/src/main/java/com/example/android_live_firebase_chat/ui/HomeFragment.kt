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
import com.example.android_live_firebase_chat.model.Profile

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
        setupChatList()
        setButtonLogoutOnClickListener()
    }

    private fun setupChatList() {
        viewModel.profileRef.addSnapshotListener { value, error ->
            if (error == null && value != null) {
                val userList = value.map { it.toObject(Profile::class.java) }.toMutableList()
                userList.removeAll { it.userId == viewModel.currentUser.value!!.uid }
                viewBinding.rvUsers.adapter = UserAdapter(userList, viewModel)
            }
        }
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