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

    /**
     * TODO:
     *  -   Logout
     *  - beim click auf logout button
     *      1. logout von firebase
     *      2. navigieren zum loginView
     */

    /**
     * TODO:
     *  -   1 .Zeige alle erstellten Benutzer in einer Lsite an.
     *  -   2. Aktualisiere die Lister der Benuzter bei Änderungen der Sammlung profiles
     */

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

        setLogoutButtonOnClickListener()
        setupChatList()
        addObservers()
    }

    private fun setupChatList() {
        viewModel.profileCollectionReference.addSnapshotListener { value, error ->
            if (value != null && error == null) {
                val profileList = value.map { it.toObject(Profile::class.java) }.toMutableList()
                profileList.removeAll { it.userId == viewModel.currentUser.value!!.uid }
                viewBinding.rvUsers.adapter = UserAdapter(profileList, viewModel)
            }
        }
    }

    private fun addObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser == null) {
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }

    private fun setLogoutButtonOnClickListener() {
        viewBinding.btLogout.setOnClickListener {
            viewModel.logout()
        }
    }
}