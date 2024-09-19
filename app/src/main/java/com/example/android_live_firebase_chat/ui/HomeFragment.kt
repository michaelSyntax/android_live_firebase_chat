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
import com.example.android_live_firebase_chat.adapter.ProfileAdapter
import com.example.android_live_firebase_chat.databinding.FragmentHomeBinding
import com.example.android_live_firebase_chat.model.Profile

class HomeFragment : Fragment() {
    /**
     * TODO:
     * 1. Button Logout mit CL und call VM logout.
     * 2. Liste an Profilen anzeigen.
     *      2.1 ProfileAdapter anlegen.
     * 3. CurrentUser beobachten und evtl. navigieren.
     */

    private lateinit var viewBinding: FragmentHomeBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btLogout.setOnClickListener {
            viewModel.logout()
        }

        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user == null) {
                findNavController().navigate(R.id.loginFragment)
            }
        }

        viewModel.profilesRef.addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null) {
                val userList = snapshot.toObjects(Profile::class.java)
                viewBinding.rvUsers.adapter = ProfileAdapter(userList, viewModel)
            }
        }
    }
}